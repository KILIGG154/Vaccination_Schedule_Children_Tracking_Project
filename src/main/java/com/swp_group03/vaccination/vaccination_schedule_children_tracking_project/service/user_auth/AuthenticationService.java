package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.JwtConfig;
import com.nimbusds.jwt.SignedJWT;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.JwtConfig;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AuthenticationRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.IntrospectRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.AuthenticationResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.IntrospectResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationService {
    UserRepo userRepo;

//    @NonFinal
//    @Value("${jwt.secretKey}")
//    protected String SIGNER_KEY;

    //Mọi thứ liên quan đến JWT thì nên tìm và chỉnh thông qua 1 nơi duy nhất là JwtConfig
    @Autowired
    JwtConfig jwtConfig;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXIT));

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

        JWSVerifier verifier = new MACVerifier(jwtConfig.getSignerKey().getBytes());

        SignedJWT signedJWT = SignedJWT.parse(request.getToken());

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifed = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verifed && expiryTime.after(new Date()))
                .build();

    }


    private String generateToken(Account account){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Data trong body (Payload của JWT gọi là Claim, tập hợp 1 chuỗi thành 1 Set)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("swp_group3.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customeClain", "Custom")
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create Token", e);
            throw new RuntimeException(e);
        }
    }

//    private String buildScope (Account account) {
//        //Vì Scope là mỗi chuỗi dài những Role và Permission nên xài String Joiner
//        StringJoiner joiner = new StringJoiner(" "); //Dấu cách để phân biệt giữa các attribute có trong scope theo quy định của Oauth2
//
//        if(!CollectionUtils.isEmpty(account.getRoles()))
//            Role role = account.getRoles();
//            account.getRoles().g .forEach(joiner::add);
//
//    }

}
