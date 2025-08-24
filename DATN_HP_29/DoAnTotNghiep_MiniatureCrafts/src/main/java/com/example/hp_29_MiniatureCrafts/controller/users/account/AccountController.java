package com.example.hp_29_MiniatureCrafts.controller.users.account;

import com.example.hp_29_MiniatureCrafts.service.account.AccountService;
import com.example.hp_29_MiniatureCrafts.service.account.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("MiniatureCrafts/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    // Gửi OTP tới email
    @PostMapping("/forgot-password/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email, @RequestParam String username) {
        return accountService.sendOtpToEmail(email , username);
    }

    // Xác thực OTP
    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        return accountService.verifyOtp(username, otp);
    }

    // Đổi mật khẩu mới sau khi xác thực OTP
    @PostMapping("/forgot-password/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        return accountService.resetPassword(email, newPassword);
    }
}
