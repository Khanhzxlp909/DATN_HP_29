package com.example.hp_29_MiniatureCrafts.service.account;


import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.example.hp_29_MiniatureCrafts.repository.auth.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account updateAccount(Account account){
        return accountRepository.save(account);
    }
}




