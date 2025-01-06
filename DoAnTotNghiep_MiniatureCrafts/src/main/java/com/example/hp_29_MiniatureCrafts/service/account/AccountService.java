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
        return accountRepository.save(account);
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




