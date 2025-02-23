package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.UserRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Account createAccount(UserRequest request){

        if(userRepo.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
//        account.setPassword(request.getPassword());
        account.setFirstName(request.getFirst_Name());
        account.setLastName(request.getLast_Name());
        account.setEmail(request.getEmail());
        account.setPhone_number(request.getPhone_number());
        account.setAddress(request.getAddress());
        account.setGender(request.getGender());
        account.setUrlimage(request.getUrl_image());
        account.setStatus(true);
        return userRepo.save(account);
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
    }

    public  Account updateAccount(UserUpdate account, String account_id){
        Account accountID =  userRepo.findById(account_id).orElseThrow(() -> new AppException(
                ErrorCode.USER_NOT_FOUND
        ));

        return userRepo.save(toUser(account, accountID));
    }

    public List<Account> getAllAccount(){
        return userRepo.findAll();
    }


    private Account toUser(UserUpdate account, Account accountID){
            // Update password only if provided and not empty
            if (account.getPassword() != null && !account.getPassword().isEmpty()) {
                accountID.setPassword(account.getPassword());
            }

            // Update other fields only if they are not null
            if (account.getFirst_Name() != null) {
                accountID.setFirstName(account.getFirst_Name());
            }
            if (account.getLast_Name() != null) {
                accountID.setLastName(account.getLast_Name());
            }
            if (account.getEmail() != null) {
                accountID.setEmail(account.getEmail());
            }
            if (account.getPhone_number() != null) {
                accountID.setPhone_number(account.getPhone_number());
            }
            if (account.getAddress() != null) {
                accountID.setAddress(account.getAddress());
            }
            if (account.getGender() != null) {
                accountID.setGender(account.getGender());
            }
            if (account.getUrl_image() != null) {
                accountID.setUrlimage(account.getUrl_image());
            }

            accountID.setStatus(account.isStatus());

            return accountID;

    }

//    public Account deactiveAccount(String id,  UserUpdate account){
//        Account accountID =  userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
//
//        return userRepo.save(toUser(account, accountID));
//
//    }
}
