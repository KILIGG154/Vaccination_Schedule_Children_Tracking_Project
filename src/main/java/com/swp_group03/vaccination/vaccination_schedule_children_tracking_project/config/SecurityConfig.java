package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;


@Configuration
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = {"/users/register", "/auth/login", "/auth/introspect" };

    private final String[] SWAGGER_ENDPOINTS = {"/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"};

    @Value("${jwt.secretKey}")
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS ).permitAll()
                        .requestMatchers(SWAGGER_ENDPOINTS).permitAll() // Cho phép truy cập Swagger UI
                        .anyRequest().authenticated());


        //Dùng decoder để giải mã token ở Bearer token
        http.oauth2ResourceServer(oath2 -> oath2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
        //jwkSetUri = "https://www.googleapis.com/oauth2/v3/certs". Dùng để cấu hình cho bên thứ 3 login!

        http.csrf(AbstractHttpConfigurer::disable);


        return http.build();
    }

    //Vì Decoder ở đây là một class abstract nên không thể tạo bean trực tiếp được vì thể ta phải tạo 1 bean method() rồi truyền vào nó!!!

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173 ")); // Cho phép FE truy cập
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Thêm OPTIONS
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Thêm max age cho preflight request

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}








//http
//                .csrf(csrf -> csrf.disable()) // Tắt CSRF để tránh lỗi khi gửi request từ Postman
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Cho phép tất cả API không cần xác thực
//                .formLogin(formLogin -> formLogin.disable()); // Tắt form login
//
//        return http.build();
