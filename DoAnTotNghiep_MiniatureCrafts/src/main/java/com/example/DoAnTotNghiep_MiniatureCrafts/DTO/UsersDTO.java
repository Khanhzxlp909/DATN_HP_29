package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.util.Date;

public class UsersDTO {
    private Integer ID;
    private String Name;
    private String Email;
    private String Username;
    private String Password;
    private RoleDTO Role;
    private Boolean IsActive;
    private Date Creation_date;
    private Date Edit_Date;

    public UsersDTO() {
    }

    public UsersDTO(Integer ID, String name, String email, String username, String password, RoleDTO role, Boolean isActive, Date creation_date, Date edit_Date) {
        this.ID = ID;
        Name = name;
        Email = email;
        Username = username;
        Password = password;
        Role = role;
        IsActive = isActive;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public RoleDTO getRole() {
        return Role;
    }

    public void setRole(RoleDTO role) {
        Role = role;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean isActive) {
        IsActive = isActive;
    }

    public Date getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(Date creation_date) {
        Creation_date = creation_date;
    }

    public Date getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(Date edit_Date) {
        Edit_Date = edit_Date;
    }
}
