package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.UserMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountCreate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public Account createAccount(AccountCreate request){

        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }

        Account account = userMapper.toCreateUser(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));


        //Tạm thời test Role thôi chứ không chốt làm như thế này
        Role userRole = roleRepo.findByRoleName("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleRepo.save(userRole);

        }
            HashSet<Role> roles = new HashSet<>();
            roles.add(userRole); // Sử dụng userRole đã tìm thấy hoặc tạo mới
            account.setRoles(roles);
            account.setStatus(true);

        return userRepo.save(account);

    }


    @PreAuthorize("hasRole('USER')")
    public  AccountResponse updateAccount(AccountUpdate request, String account_id){
//        log.info("Update account with id: {}", account_id);
//        Account account =  userRepo.findById(account_id).orElseThrow(() -> new AppException(
//                ErrorCode.USER_NOT_FOUND
//        ));

//         userMapper.toUpdateUser(request, account);
//         account.setPassword(passwordEncoder.encode(request.getPassword()));


        return userMapper.toAccountResponse(userRepo.updateById(account_id, request)) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> getAllAccount(){

        List<AccountResponse> accounts = userMapper.toAllAccountResponse(userRepo.findAll());

          if (accounts !=null){
              return userMapper.toAllAccountResponse(userRepo.findAll());
            }else {
              throw new AppException(ErrorCode.EMPTY_USER);
          }
    }

    @PostAuthorize("returnObject.name == authentication.name")
    public AccountResponse getAccountById(String id){
        Account account = userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        return userMapper.toAccountResponse(userRepo.findById(id));
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
            log.warn("Admin user has been created with default password 'admin'. Please change it immediately.");
        } else {
            log.info("Admin account already exists, skipping creation.");
        }
    }

    public AccDTO getChildByAccId(String accountId){
        Account account = userRepo.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new AccDTO(account);
    }

}
