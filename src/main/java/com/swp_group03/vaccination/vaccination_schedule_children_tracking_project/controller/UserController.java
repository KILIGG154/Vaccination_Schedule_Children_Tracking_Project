package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity registerUser(@Valid @RequestBody UserRequest request){
        Account newUser = userService.createAccount(request);
       return ResponseEntity.ok(newUser);
    }

    @PatchMapping("{account_id}")
    public ResponseEntity updateUser(@PathVariable String account_id, @Validated @RequestBody UserUpdate request){
        return ResponseEntity.ok(userService.updateAccount(request,account_id));
    }



}
