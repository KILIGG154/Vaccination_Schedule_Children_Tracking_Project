package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;
//
//
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để tránh lỗi khi gửi request từ Postman
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Cho phép tất cả API không cần xác thực
                .formLogin(formLogin -> formLogin.disable()); // Tắt form login

        return http.build();
    }
}

