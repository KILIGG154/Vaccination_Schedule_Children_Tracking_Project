package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.UserMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountCreate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final List<String> VALID_ROLES = Arrays.asList("ADMIN", "USER", "DOCTOR", "NURSE");
    private static final List<String> VALID_ROLES_FOR_UPDATE = Arrays.asList("ADMIN", "DOCTOR", "NURSE");

    @Transactional
    public Account createAccount(AccountCreate request){
        // Validate role
        validateRole(request.getRoleName());

        // Validate if username already exists
        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }

        // Create account based on request
        Account account = userMapper.toCreateUser(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setStatus(true);

        // Set up role for the account
        String roleName = request.getRoleName();
        if (roleName == null || roleName.isEmpty()) {
            // Default to USER if no role specified
            roleName = "USER";
        }

        Role role = roleRepo.findByRoleName(roleName);
        if (role == null) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }

        // Set up roles for the account
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        account.setRoles(roles);
        
        // Save the account
        log.info("Creating new account with role: {}", roleName);
        return userRepo.save(account);
    }

    @Transactional
    public AccountResponse updateAccount(AccountUpdate request, String account_id) {
        log.info("Update account with id: {}", account_id);
        
        // Tìm account
        Account account = userRepo.findById(account_id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Validate role nếu được cung cấp
        if (request.getRoleName() != null) {
            // Kiểm tra role có hợp lệ không
            if (!VALID_ROLES_FOR_UPDATE.contains(request.getRoleName())) {
                throw new AppException(ErrorCode.INVALID_ROLE);
            }

            // Tìm và cập nhật role
            Role newRole = roleRepo.findByRoleName(request.getRoleName());
            if (newRole == null) {
                throw new AppException(ErrorCode.ROLE_NOT_FOUND);
            }
            
            // Xóa các role cũ và thêm role mới
            account.getRoles().clear();
            account.getRoles().add(newRole);
        }

        // Cập nhật thông tin từ request
        userMapper.toUpdateUser(request, account);

        // Lưu và trả về account đã cập nhật
        return userMapper.toAccountResponse(userRepo.save(account));
    }

    public List<AccountResponse> getAllAccount(){
        List<AccountResponse> accounts = userMapper.toAllAccountResponse(userRepo.findAll());

          if (accounts !=null){
              return userMapper.toAllAccountResponse(userRepo.findAll());
            }else {
              throw new AppException(ErrorCode.EMPTY_USER);
          }
    }

    public AccountResponse getAccountById(String id){
        Account account = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toAccountResponse(userRepo.findByAccountId(id));
    }

    public AccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Account account = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toAccountResponse(Optional.of(account));
    }

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
            log.warn("Admin user has been created with default password 'admin123'. Please change it immediately.");
        } else {
            log.info("Admin account already exists, skipping creation.");
        }
    }

    public AccDTO getChildByAccId(String accountId){
        Account account = userRepo.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new AccDTO(account);
    }

    // Thêm method validate role để tái sử dụng
    private void validateRole(String roleName) {
        if (roleName != null && !VALID_ROLES.contains(roleName)) {
            throw new AppException(ErrorCode.INVALID_ROLE);
        }
    }
}
