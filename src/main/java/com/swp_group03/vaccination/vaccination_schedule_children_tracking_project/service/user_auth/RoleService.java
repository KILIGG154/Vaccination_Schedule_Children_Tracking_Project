package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdminAccountIfNotExists() {
        if (!userRepo.existsByUsername("admin")) {
            log.info("Admin account not found, creating default admin user...");

            // Ensure ADMIN role exists
            Role adminRole = roleRepo.findByRoleName("ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRoleName("ADMIN");
                adminRole = roleRepo.save(adminRole);
            }

            // Create Admin Account
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@gmail.com");
            admin.setPhoneNumber("0903731347");
            admin.setAddress("HCM");
            admin.setGender(Gender.OTHER);
            admin.setStatus(true);

            // Properly manage roles
            admin.setRoles(new HashSet<>());
            admin.getRoles().add(adminRole);

            // Save Admin Account
            userRepo.save(admin);
            log.warn("Admin user has been created with default password 'admin'. Please change it immediately.");
        } else {
            log.info("Admin account already exists, skipping creation.");
        }
    }
} 