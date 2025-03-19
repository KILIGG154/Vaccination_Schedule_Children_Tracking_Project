package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.RoleService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final UserService userService;
    private final RoleService roleService;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            // Initialize all required roles first
            roleService.initializeRoles();
            
            // Create admin account
            userService.createAdminAccountIfNotExists();
        };
    }
}
