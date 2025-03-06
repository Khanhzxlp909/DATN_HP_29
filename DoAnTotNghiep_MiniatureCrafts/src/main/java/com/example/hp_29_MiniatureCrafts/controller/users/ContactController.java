package com.example.hp_29_MiniatureCrafts.controller.users;


import com.example.hp_29_MiniatureCrafts.dto.ContactRequest;
import com.example.hp_29_MiniatureCrafts.service.account.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MiniatureCrafts/contact")
public class ContactController {
    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendContactEmail(@RequestBody ContactRequest request) {
        try {
            emailService.contactToAdmin(request);
            return ResponseEntity.ok("Gửi liên hệ thành công!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Lỗi gửi email: " + e.getMessage());
        }
    }

}
