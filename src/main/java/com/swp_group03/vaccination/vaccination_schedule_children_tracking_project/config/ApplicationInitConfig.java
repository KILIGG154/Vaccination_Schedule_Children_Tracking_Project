package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    UserRepo userRepo;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (!userRepo.existsByUsername("admin")) {
                Account a = new Account();
                a.setUsername("admin");
                a.setPassword(passwordEncoder.encode("admin"));
                a.setFirstName("admin");
                a.setLastName("admin");
                a.setEmail("admin@gmail.com");
                a.setPhoneNumber("0903731347");
                a.setAddress("HCM");
                a.setGender(Gender.MALE);
                a.setStatus(true);
                a.setUrlImage("blablablablalba");

                // Tìm kiếm Role "ADMIN"
                Role role = roleRepo.findByRoleName("ADMIN");
                if (role == null) {
                    // Nếu Role chưa tồn tại, tạo mới
                    role = new Role();
                    role.setRoleName("ADMIN");
                    roleRepo.save(role); // Lưu Role mới vào cơ sở dữ liệu
                }

                // Gán Role cho Account
                HashSet<Role> roles = new HashSet<>();
                roles.add(role); // Sử dụng Role đã tìm thấy hoặc tạo mới
                a.setRoles(roles);

                // Lưu Account vào cơ sở dữ liệu
                userRepo.save(a);
                log.warn("admin user has been created with default password admin, please change it!");
            }
        };
    }
}