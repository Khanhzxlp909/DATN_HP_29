package com.example.hp_29_MiniatureCrafts.service.account;

//import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    public String getRole(List<String> roles) {
        for (String role : roles) {
            if (role.equals("ADMIN")) {
                System.out.println("ADMIN");
                return "ADMIN";

            } else if (role.equals("CUSTOMER")) {
                System.out.println("Customer");
                return "Customer";
            } else {
                return "Users";
            }
        }
        return "unknown";
    }


}
