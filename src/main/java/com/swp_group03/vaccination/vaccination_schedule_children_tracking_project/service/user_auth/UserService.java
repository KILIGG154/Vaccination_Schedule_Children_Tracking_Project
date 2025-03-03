package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.UserMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserCeation;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.RoleRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
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


    public Account createAccount(UserCeation request){

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


//    @PreAuthorize("hasRole('USER')")
    public  Account updateAccount(UserUpdate account, String account_id){
        log.info("Update account with id: {}", account_id);
        Account accountID =  userRepo.findById(account_id).orElseThrow(() -> new AppException(
                ErrorCode.USER_NOT_FOUND
        ));

         accountID = userMapper.toUpdateUser(account);

        return userRepo.save(accountID);
    }

//    @PostAuthorize("returnObject.name == authentication.name")
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
//        return userMapper.toAccountResponse(userRepo.findById(id));
        return userMapper.toAccountResponsee(userRepo.findByAccountId(id));
    }


    public AccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Account account = userRepo.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toAccountResponse(Optional.of(account));
    }


//        Account account = new Account();
//        account.setUsername(request.getUsername())
//        Account account = new Account();
//        account.setUsername(request.getUsername());
//        account.setPassword(request.getPassword());
//        account.setFirst_Name(request.getFirst_Name());
//        account.setLast_Name(request.getLast_Name());
//        account.setEmail(request.getEmail());
//        account.setPhone_number(request.getPhone_number());
//        account.setAddress(request.getAddress());
//        account.setGender(request.getGender());
//        account.setUrl_image(request.getUrl_image());
//        account.setStatus(true);
//        return userRepo.save(account);
//===============================
//    private Account toUser(UserUpdate account, Account accountID){
//            // Update password only if provided and not empty
//            if (account.getPassword() != null && !account.getPassword().isEmpty()) {
//                accountID.setPassword(account.getPassword());
//            }
//
//            // Update other fields only if they are not null
//            if (account.getFirst_Name() != null) {
//                accountID.setFirstName(account.getFirst_Name());
//            }
//            if (account.getLast_Name() != null) {
//                accountID.setLastName(account.getLast_Name());
//            }
//            if (account.getEmail() != null) {
//                accountID.setEmail(account.getEmail());
//            }
//            if (account.getPhone_number() != null) {
//                accountID.setPhoneNumber(account.getPhone_number());
//            }
//            if (account.getAddress() != null) {
//                accountID.setAddress(account.getAddress());
//            }
//            if (account.getGender() != null) {
//                accountID.setGender(account.getGender());
//            }
//            if (account.getUrl_image() != null) {
//                accountID.setUrlImage(account.getUrl_image());
//            }
//
//            accountID.setStatus(account.isStatus());
//
//            return accountID;
//
//    }

//    public Account deactiveAccount(String id,  UserUpdate account){
//        Account accountID =  userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
//
//        return userRepo.save(toUser(account, accountID));
//
//    }
}
