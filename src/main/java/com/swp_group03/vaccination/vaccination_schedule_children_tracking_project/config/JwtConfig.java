package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.secretKey}")
    private String SIGNER_KEY;

    public String getSignerKey() {
        return SIGNER_KEY;
    }

    //Vì Decoder ở đây là một class Interface nên không thể tạo bean trực tiếp được vì thể ta phải tạo 1 bean method() rồi truyền vào nó!!!

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }

    //Tương tự Decoder Converter cũng là Class Interface không tạo trực tiếp truyền biến được (ko có constructor để truyền vào)
    //Nên ta phải tạo 1 bean method() rồi truyền vào nó!!!
    @Bean
    public JwtAuthenticationConverter jwtConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        //Customize ClaimName (Name của Claim về Author trong Payload của Token)
        //Delimiter (khoảng cách giữa các Attribute của Claim)
        //Prefix (Điều chỉnh name Attribute của Claim)
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
