package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;

import com.nimbusds.jose.JOSEException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AuthenticationRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.IntrospectRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.AuthenticationResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.IntrospectResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.AuthenticationService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)  {

        var isAuthen = authenticationService.authenticate(request);

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .code(100)
                .message("Login successfully: ")
                .result(isAuthen)
                .build();

        return apiResponse;
    }

@PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {

        var isValid = authenticationService.introspect(request);
        return  ApiResponse.<IntrospectResponse>builder()
                .code(100)
                .message("Introspect successfully: ")
                .result(isValid)
                .build();
    }
}




//        if(isAuthen) {
//            apiResponse.setCode(100);
//            apiResponse.setMessage("Login successfully: " +isAuthen);
//        } else {
//            throw new AppException(ErrorCode.WRONG_PASSWORD);
//        }