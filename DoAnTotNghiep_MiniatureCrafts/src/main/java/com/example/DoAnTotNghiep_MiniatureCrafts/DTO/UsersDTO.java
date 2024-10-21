package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private int id;
    private String name;
    private String email;
    private String username;
    private RoleDTO role;
    private boolean isActive;
    private Date creationDate;
    private Date editDate;


}
