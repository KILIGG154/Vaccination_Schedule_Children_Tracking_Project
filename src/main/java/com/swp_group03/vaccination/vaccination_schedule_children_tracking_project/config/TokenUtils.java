package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.JwtConfig;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class TokenUtils {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * Tạo token reset password cho quá trình đặt lại mật khẩu
     * Token này có hiệu lực ngắn (15 phút) và chỉ dùng để đặt lại mật khẩu
     */
    public String generateResetPasswordToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Data trong payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("swp_group3.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli())) // Hết hạn sau 15 phút
                .jwtID(UUID.randomUUID().toString())
                .claim("email", account.getEmail())
                .claim("type", "password_reset") // Chỉ định loại token là reset password
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create reset password token", e);
            throw new RuntimeException(e);
        }
    }
}