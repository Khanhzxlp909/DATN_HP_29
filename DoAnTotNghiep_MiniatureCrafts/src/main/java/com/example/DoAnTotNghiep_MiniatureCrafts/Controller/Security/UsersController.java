package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Security;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.UsersDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public List<UsersDTO> findAllByUsername(@RequestBody UsersDTO users) {
        String username = users.getUsername();
        String password = users.getPassword();
        List<UsersDTO> lstUsers =  userService.findAllByUsername(username, password);;

        for (UsersDTO user : lstUsers) {
            System.out.println("ID: " + user.getID());
            System.out.println("\n Name: " + user.getUsername());
            System.out.println("\n username: " + user.getUsername());
            System.out.println("\n password: " + user.getPassword());
            System.out.println("\n email: " + user.getEmail());
            System.out.println("\n Role: " + user.getRole());
        }

        return lstUsers;
    }



}
