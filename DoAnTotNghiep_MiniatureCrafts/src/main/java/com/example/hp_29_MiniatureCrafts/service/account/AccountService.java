package com.example.hp_29_MiniatureCrafts.service.account;


import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.repository.auth.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder encoder;

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
}




