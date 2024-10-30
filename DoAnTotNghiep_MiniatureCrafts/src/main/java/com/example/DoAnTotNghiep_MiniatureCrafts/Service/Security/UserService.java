package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.RoleDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.UsersDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public UsersDTO findAllByUsername(String username, String password) {
        Users user = userRepository.loginByUsername(username, password);

            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setID(user.getID());
            usersDTO.setName(user.getName());
            usersDTO.setEmail(user.getEmail());
            usersDTO.setUsername(user.getUsername());
            usersDTO.setPassword(user.getPassword());
            usersDTO.setRole( new RoleDTO(
                    user.getRole().getID(),
                    user.getRole().getCanCreate(),
                    user.getRole().getCanUpdate(),
                    user.getRole().getCanDelete()
            ));
            usersDTO.setIsActive(user.getActive());
            usersDTO.setCreation_date(user.getCreation_date());
            usersDTO.setEdit_Date(user.getEdit_Date());
            return usersDTO;


    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = userRepository.findByUsername(username);
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                getAuthorities(user)
//        );
//    }


}
