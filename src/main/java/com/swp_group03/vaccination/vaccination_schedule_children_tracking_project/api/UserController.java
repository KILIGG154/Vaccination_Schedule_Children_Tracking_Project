package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountCreate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management")
@Slf4j
public class  UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for normal user registration
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ApiResponse<Account> registerUser(@Valid @RequestBody AccountCreate request){
        ApiResponse<Account> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createAccount(request));
        apiResponse.setCode(100);
        apiResponse.setMessage("Registered Successfully");

       return apiResponse;
    }
    
    /**
     * Endpoint for admin to create staff accounts (DOCTOR, NURSE)
     * Only admin can access this endpoint
     */
    @PostMapping("/staff")
    @Operation(summary = "Create staff account (DOCTOR, NURSE)")
    public ApiResponse<AccountResponse> createStaffAccount(@Valid @RequestBody AccountCreate request) {
        // Make sure a role is specified and is either DOCTOR or NURSE
        String role = request.getRoleName();
        if (role == null || (!role.equals("DOCTOR") && !role.equals("NURSE"))) {
            return ApiResponse.<AccountResponse>builder()
                    .code(400)
                    .message("Staff role must be either DOCTOR or NURSE")
                    .build();
        }
        
        Account staffAccount = userService.createAccount(request);
        AccountResponse response = userService.getAccountById(staffAccount.getAccountId());
        
        return ApiResponse.<AccountResponse>builder()
                .code(200)
                .message("Staff account created successfully")
                .result(response)
                .build();
    }

    @PatchMapping("/{accountId}")
    public ApiResponse<AccountResponse> updateUser(
        @PathVariable String accountId, 
        @Validated @RequestBody AccountUpdate request
    ){
        try {
            AccountResponse updatedAccount = userService.updateAccount(request, accountId);
            return ApiResponse.<AccountResponse>builder()
                .code(200)
                .message("Account updated successfully")
                .result(updatedAccount)
                .build();
        } catch (AppException e) {
            return ApiResponse.<AccountResponse>builder()
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();
        }
    }

    @GetMapping("/getAllUser")
    public ApiResponse<List<AccountResponse>> getAllAccount(){

//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("User Authorities: {}", authentication.getAuthorities());
//
//        log.info("User: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info("Granted Authority: {}", grantedAuthority.getAuthority()));

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

    @GetMapping("/{accountId}/children")
    public ApiResponse<AccDTO> getChildrenByAccount(@PathVariable String accountId){
        return ApiResponse.<AccDTO>builder()
                .code(200)
                .message("Children retrieved successfully")
                .result(userService.getChildByAccId(accountId))
                .build();
    }
}
