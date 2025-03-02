package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;


@Configuration
public class SecurityConfig {

    private final String[] PUBLIC_ENPOINTS = {"/users/register", "/auth/login", "/auth/introspect" };

    private final String[] PRIVATE_ENPOINTS = {"/users", "/vaccine/addVaccine", "/vaccine/get" };

    private final String[] SWAGGER_ENDPOINTS = {"/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"};

    //Mọi thứ liên quan đến JWT thì nên tìm và chỉnh thông qua 1 nơi duy nhất là JwtConfig
    @Autowired
    JwtConfig jwtConfig;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConverter jwtConverter) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS ).permitAll() // Cho phép truy cập các API public
//
//                        .requestMatchers(HttpMethod.GET, PRIVATE_ENPOINTS)
////                        .hasAuthority("ROLE_ADMIN") // Chỉ cho phép truy cập các API private với quyền ADMIN
//                        .hasRole("ADMIN")

                        .requestMatchers(SWAGGER_ENDPOINTS).permitAll() // Cho phép truy cập Swagger UI
                        .anyRequest().authenticated());


        //Dùng decoder để giải mã token ở Bearer token
        http.oauth2ResourceServer(oath2 -> oath2.jwt(jwtConfigurer
                -> jwtConfigurer.decoder(jwtConfig.jwtDecoder())
                .jwtAuthenticationConverter(jwtConfig.jwtConverter()))

        );
        //jwkSetUri = "https://www.googleapis.com/oauth2/v3/certs". Dùng để cấu hình cho bên thứ 3 login!

        //Tắt CSRF để tránh lỗi khi gửi request từ Postman và Swagger UI (vì nó không hỗ trợ CSRF) (Cross-Site Request Forgery) (Tấn công giả mạo yêu cầu giữa các trang)
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    //Cấu hình Cors để không chặng truy cập từ FE (xung đột policy giữa BE và FE)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173 ")); // Cho phép FE truy cập
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Thêm OPTIONS
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}



//http
//                .csrf(csrf -> csrf.disable()) // Tắt CSRF để tránh lỗi khi gửi request từ Postman
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Cho phép tất cả API không cần xác thực
//                .formLogin(formLogin -> formLogin.disable()); // Tắt form login
//
//        return http.build();
