package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.JwtConfig;
import com.nimbusds.jwt.SignedJWT;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.JwtConfig;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Invalidatedtoken;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AuthenticationRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.IntrospectRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.LogoutRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.RefreshRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AuthenticationResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.IntrospectResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.InvalidatedTokenRepository;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class AuthenticationService {
    UserRepo userRepo;
    InvalidatedTokenRepository tokenRepository;

//    @NonFinal
//    @Value("${jwt.secretKey}")
//    protected String SIGNER_KEY;

    //Mọi thứ liên quan đến JWT thì nên tìm và chỉnh thông qua 1 nơi duy nhất là JwtConfig
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXIST));

         boolean authenticated = jwtConfig.passwordEncoder().matches(request.getPassword(), account.getPassword());

    if(!authenticated) {
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

        var token = generateToken(account);

        return AuthenticationResponse.builder()
                .token(token).authenticated(authenticated)
                .build();
    }


    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean invalid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            invalid = false;
        }

        return IntrospectResponse.builder()
                .valid(invalid)
                .build();

    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryDate = signToken.getJWTClaimsSet().getExpirationTime();

        Invalidatedtoken invalidatedtoken = Invalidatedtoken.builder()
                .id(jit)
                .expiryTime(expiryDate)
                .build();

        tokenRepository.save(invalidatedtoken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtConfig.getSignerKey().getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifed = signedJWT.verify(verifier);
        if(!(verifed && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken());

        var jwt = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        Invalidatedtoken invalidatedtoken = Invalidatedtoken.builder()
                .id(jwt)
                .expiryTime(expiryTime)
                .build();

        tokenRepository.save(invalidatedtoken);


        var username = signedJWT.getJWTClaimsSet().getStringClaim("username").toString();

        var user = userRepo.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Data trong body (Payload của JWT gọi là Claim)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(account.getAccountId())) // Chỉ để accountId làm subject
                .issuer("swp_group3.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("username", account.getUsername()) // Thêm username vào claim riêng
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create Token", e);
            throw new RuntimeException(e);
        }
    }


    private String buildScope (Account account) {
        //Vì Scope là mỗi chuỗi dài những Role và Permission nên xài String Joiner
        StringJoiner joiner = new StringJoiner(" "); //Dấu cách để phân biệt giữa các attribute có trong scope theo quy định của Oauth2

        if(!CollectionUtils.isEmpty(account.getRoles()))
            account.getRoles().forEach(role ->{joiner.add(role.getRoleName());

            });
        return joiner.toString();
    }

}
