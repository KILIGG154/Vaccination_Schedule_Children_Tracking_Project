// ResetPasswordService.java
package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OTPEntity;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.OTPRepository;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class ResetPasswordService {
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private OTPRepository otpRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenUtils tokenUtils;
    
    private Random random = new Random();
    
    /**
     * Tìm account theo email
     */
    public Account findAccountByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
    }
    
    /**
     * Tạo và gửi OTP đến email
     */
    public void sendOTP(String email) {
        // Kiểm tra email tồn tại
        Account account = findAccountByEmail(email);
        
        // Tạo OTP 6 chữ số
        String otp = String.format("%06d", random.nextInt(1000000));
        
        // Lưu OTP vào database, có thời hạn 5 phút
        OTPEntity otpEntity = OTPEntity.builder()
                .email(email)
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .isUsed(false)
                .build();
        
        otpRepository.save(otpEntity);
        
        // Gửi OTP qua email
        try {
            sendOTPEmail(email, otp, account.getFirstName() + " " + account.getLastName());
        } catch (MessagingException e) {
            log.error("Failed to send OTP email", e);
            throw new AppException(ErrorCode.EMAIL_SENDING_ERROR);
        }
    }
    
    /**
     * Gửi email chứa OTP
     */
    private void sendOTPEmail(String email, String otp, String name) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(email);
        helper.setSubject("Mã xác nhận đặt lại mật khẩu");
        
        String content = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<div style='background-color: #1a56db; color: white; padding: 20px; text-align: center;'>"
                + "<h1 style='margin: 0;'>Đặt Lại Mật Khẩu</h1>"
                + "</div>"
                + "<div style='padding: 20px; border: 1px solid #e5e7eb; border-top: none;'>"
                + "<p>Xin chào " + name + ",</p>"
                + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại VaccineCare.</p>"
                + "<p>Đây là mã xác nhận (OTP) của bạn:</p>"
                + "<div style='margin: 30px 0; text-align: center;'>"
                + "<div style='display: inline-block; padding: 15px 30px; background-color: #f3f4f6; font-size: 24px; font-weight: bold; letter-spacing: 5px;'>"
                + otp
                + "</div>"
                + "</div>"
                + "<p>Mã này sẽ hết hạn sau 5 phút.</p>"
                + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>"
                + "<p>Trân trọng,<br>Đội ngũ VaccineCare</p>"
                + "</div>"
                + "</div>";
        
        helper.setText(content, true);
        mailSender.send(message);
    }
    
    /**
     * Xác thực OTP
     */
    public String verifyOTP(String email, String otp) {
        // Tìm OTP mới nhất chưa sử dụng
        OTPEntity otpEntity = otpRepository.findByEmailAndOtpAndIsUsedFalse(email, otp)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));
        
        // Kiểm tra OTP hết hạn
        if (otpEntity.isExpired()) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        
        // Đánh dấu OTP đã sử dụng
        otpEntity.setUsed(true);
        otpRepository.save(otpEntity);
        
        // Tạo token tạm thời để reset password
        Account account = findAccountByEmail(email);
        return tokenUtils.generateResetPasswordToken(account);
    }
    
    /**
     * Đặt lại mật khẩu
     */
    public void resetPassword(String email, String newPassword) {
        Account account = findAccountByEmail(email);
        
        // Mã hóa mật khẩu mới
        account.setPassword(passwordEncoder.encode(newPassword));
        
        // Lưu tài khoản với mật khẩu mới
        userRepo.save(account);
    }
}