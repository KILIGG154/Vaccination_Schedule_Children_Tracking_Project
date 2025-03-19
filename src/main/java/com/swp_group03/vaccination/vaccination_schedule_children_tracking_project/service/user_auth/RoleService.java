package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.RoleMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.RoleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.RoleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepo roleRepo;
    RoleMapper roleMapper;

    /**
     * Initialize system roles
     */
    @Transactional
    public void initializeRoles() {
        List<String> systemRoles = Arrays.asList("ADMIN", "USER", "DOCTOR", "NURSE");
        
        for (String roleName : systemRoles) {
            if (roleRepo.findByRoleName(roleName) == null) {
                log.info("Creating {} role", roleName);
                Role role = new Role();
                role.setRoleName(roleName);
                roleRepo.save(role);
            }
        }
        
        log.info("All system roles have been initialized");
    }

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);
        return roleMapper.toRoleResponse(roleRepo.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepo.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
} 