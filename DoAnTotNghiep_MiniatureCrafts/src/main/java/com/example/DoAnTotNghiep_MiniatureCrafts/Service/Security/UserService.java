package com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.RoleDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.UsersDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<UsersDTO> findAllByUsername(String username, String password) {
        List<Users> lstUsers = userRepository.loginByUsername(username, password);

        return lstUsers.stream().map(user -> {
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

        }).collect(Collectors.toList());

    }
}
