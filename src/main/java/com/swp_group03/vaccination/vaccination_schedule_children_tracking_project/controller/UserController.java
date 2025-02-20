package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.UserRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRequest request){
       return ResponseEntity.ok(userService.createAccount(request));
    }


}
