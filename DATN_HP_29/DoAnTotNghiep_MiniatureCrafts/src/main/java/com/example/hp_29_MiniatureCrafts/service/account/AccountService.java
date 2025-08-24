package com.example.hp_29_MiniatureCrafts.service.account;


import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.repository.auth.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    // Lưu OTP tạm thời (email -> otp)
    private Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public Account updateAccount(Account account){
        Account acc = accountRepository.findByUsername(account.getUsername());
        return accountRepository.save(account);
    }

    public Account checkPassword(String username, String password) {
        System.out.println("username log 24: " + username);
        System.out.println("password log 25: " + password);
        Account account = accountRepository.findByUsername(username);
        System.out.println("Account log 27: " + account.getPassword());
        if (account != null && encoder.matches(password, account.getPassword())) { // Dùng matches để kiểm tra
            return account;
        }
        throw new RuntimeException("Password is incorrect");
    }

    public Account changePassword(String username, String newPassword) {
        Account account = accountRepository.findByUsername(username);
        System.out.println("Account: " + account.getUsername());
        System.out.println("Account ID: " + account.getID());
        account.setPassword(encoder.encode(newPassword));
        System.out.println("accout new password: "+ account.getPassword());
        return accountRepository.save(account);
    }

    // Gửi OTP tới email
    public ResponseEntity<?> sendOtpToEmail( String email, String username ) {
        System.out.println("email: " + email);
        System.out.println("username: " + username);
        Account account = accountRepository.findByEmailAndUsername(email, username);
        if (account == null) {
            return ResponseEntity.badRequest().body("username không tồn tại!");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        otpStorage.put(email, otp);
        try {
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.ok("OTP đã được gửi tới email!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Gửi OTP thất bại!");
        }
    }
    public ResponseEntity<?> verifyOtp(String username, String otp) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            return ResponseEntity.badRequest().body("username không tồn tại!");
        }
        String email = account.getEmail();
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            return ResponseEntity.ok("OTP hợp lệ!");
        }
        return ResponseEntity.badRequest().body("OTP không hợp lệ!");
    }

    // Đổi mật khẩu mới sau khi xác thực OTP
    public ResponseEntity<?> resetPassword(String email, String newPassword) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại!");
        }
        if (!otpStorage.containsKey(email)) {
            return ResponseEntity.badRequest().body("Chưa xác thực OTP!");
        }
        account.setPassword(encoder.encode(newPassword));
        accountRepository.save(account);
        otpStorage.remove(email);
        return ResponseEntity.ok("Đổi mật khẩu thành công!");
    }
}
