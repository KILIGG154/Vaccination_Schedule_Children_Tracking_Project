package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
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

}
