// ResetPasswordController.java
package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.ForgotPasswordRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.ResetPasswordRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.VerifyOTPRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.ResetPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Reset Password", description = "API quên mật khẩu")
@Slf4j
public class ResetPasswordController {
    
    @Autowired
    private ResetPasswordService resetPasswordService;
    
    @PostMapping("/forgot-password")
    @Operation(summary = "Gửi OTP đến email để đặt lại mật khẩu")
    public ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Forgot password request for email: {}", request.getEmail());
        resetPasswordService.sendOTP(request.getEmail());
        
        return ApiResponse.<Void>builder()
                .code(200)
                .message("OTP đã được gửi đến email của bạn")
                .build();
    }
    
    @PostMapping("/resend-otp")
    @Operation(summary = "Gửi lại OTP")
    public ApiResponse<Void> resendOTP(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Resend OTP request for email: {}", request.getEmail());
        resetPasswordService.sendOTP(request.getEmail());
        
        return ApiResponse.<Void>builder()
                .code(200)
                .message("OTP mới đã được gửi đến email của bạn")
                .build();
    }
    
    @PostMapping("/verify-otp")
    @Operation(summary = "Xác thực OTP")
    public ApiResponse<Map<String, String>> verifyOTP(@Valid @RequestBody VerifyOTPRequest request) {
        log.info("Verify OTP request for email: {}", request.getEmail());
        String token = resetPasswordService.verifyOTP(request.getEmail(), request.getOtp());
        
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        
        return ApiResponse.<Map<String, String>>builder()
                .code(200)
                .message("OTP hợp lệ")
                .result(result)
                .build();
    }

    @PatchMapping("/reset-password")
    @Operation(summary = "Đặt lại mật khẩu sau khi xác thực OTP")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Reset password request for email: {}", request.getEmail());
        resetPasswordService.resetPassword(request.getEmail(), request.getPassword());

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Mật khẩu đã được đặt lại thành công")
                .build();
    }
}