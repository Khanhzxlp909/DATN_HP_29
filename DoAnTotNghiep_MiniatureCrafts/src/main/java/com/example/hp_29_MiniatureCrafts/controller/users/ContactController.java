package com.example.hp_29_MiniatureCrafts.controller.users;


import com.example.hp_29_MiniatureCrafts.dto.ContactRequest;
import com.example.hp_29_MiniatureCrafts.service.account.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MiniatureCrafts/contact")
public class ContactController {
    @Autowired
    EmailService emailService;

    @GetMapping("findall")
    public List<ContactRequest> contact() {
        return emailService.findAll();
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendContactEmail(@RequestBody ContactRequest request) {
        try {
            emailService.contactToAdmin(request);
            return ResponseEntity.ok("Gửi liên hệ thành công!");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Lỗi gửi email: " + e.getMessage());
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        emailService.deleteContact(id);
        return "done";
    }

}
