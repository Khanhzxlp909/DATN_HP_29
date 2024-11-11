//package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security;
//
//import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.RoleDTO;
//import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.UsersDTO;
//import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Account;
//import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class UserService {
//
//    @Autowired
//    public UserRepository userRepository;
//
//    public UsersDTO findAllByUsername(String username, String password) {
//        Account user = userRepository.loginByUsername(username, password);
//
//        UsersDTO usersDTO = new UsersDTO();
//        usersDTO.setID(user.getID());
////        usersDTO.setName(user.getName());
////        usersDTO.setEmail(user.getEmail());
//        usersDTO.setUsername(user.getUsername());
//        usersDTO.setPassword(user.getPassword());
//
//        // set role vào roleDTO
//        Set<RoleDTO> roles = new HashSet<>();
//        user.getRoles().forEach(role -> {
//            roles.add(new RoleDTO(role.getID(), role.getName()));
//        });
//        usersDTO.setRoles(roles);
//
//        usersDTO.setActive(user.getActive());
//        usersDTO.setCreation_date(user.getCreation_date());
//        usersDTO.setEdit_Date(user.getEdit_Date());
//        return usersDTO;
//
//
//    }
////
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Users user = userRepository.findByUsername(username);
////
////        return new org.springframework.security.core.userdetails.User(
////                user.getUsername(),
////                user.getPassword(),
////                getAuthorities(user)
////        );
////    }
//
//
//}
