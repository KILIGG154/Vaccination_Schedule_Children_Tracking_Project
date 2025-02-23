package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Account createAccount(UserRequest request){
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
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
        Account accountID =  userRepo.findById(account_id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        accountID.setPassword(account.getPassword());
        accountID.setFirstName(account.getFirst_Name());
        accountID.setLastName(account.getLast_Name());
        accountID.setEmail(account.getEmail());
        accountID.setPhone_number(account.getPhone_number());
        accountID.setAddress(account.getAddress());
        accountID.setGender(account.getGender());
        accountID.setUrlimage(account.getUrl_image());
        return userRepo.save(accountID);
    }


}
