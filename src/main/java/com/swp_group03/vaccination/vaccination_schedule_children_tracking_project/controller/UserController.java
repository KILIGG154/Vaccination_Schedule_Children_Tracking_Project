package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserCeation;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ApiResponse<Account> registerUser(@Valid @RequestBody UserCeation request){
        ApiResponse<Account> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createAccount(request));
        apiResponse.setCode(100);
        apiResponse.setMessage("Registered Successfully");

       return apiResponse;
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity updateUser(@PathVariable String accountId, @Validated @RequestBody UserUpdate request){
        return ResponseEntity.ok(userService.updateAccount(request,accountId));
    }

    @GetMapping("/getAllUser")
    public ApiResponse<List<AccountResponse>> getAllAccount(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("Granted Authority: {}", grantedAuthority.getAuthority()));

        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllAccount());
        if(apiResponse.getResult().isEmpty()){
            apiResponse.setCode(98);
            apiResponse.setMessage("No Account found");
            apiResponse.setResult(null);
            return apiResponse;
        }

        apiResponse.setCode(101);
        apiResponse.setMessage("All Account retrieved");
        return apiResponse;
    }

    @GetMapping("/{accountId}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable String accountId){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAccountById(accountId));
        apiResponse.setCode(1);
        apiResponse.setMessage("Success");
        return apiResponse;
    }

    //Đợi Role trong JWT
    @GetMapping("/myInfo")
    public ApiResponse<AccountResponse> getMyInfo(){
        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        apiResponse.setCode(1);
        apiResponse.setMessage("Success");
        return apiResponse;
    }
}
