package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng
    private String Name;
    private String Email;  // Đảm bảo Email là duy nhất
    private String Username;  // Đảm bảo Username là duy nhất
    private String Password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private Boolean IsActive;
    @Column(name = "Creation_date")
    @Temporal(TemporalType.DATE)
    private Date Creation_date;

    @Column(name = "Edit_Date")
    @Temporal(TemporalType.DATE)
    private Date Edit_Date;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
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

    public Users() {
    }

    public Users(Integer ID, String name, String email, String username, String password, Boolean isActive, Date creation_date, Date edit_Date) {
        this.ID = ID;
        Name = name;
        Email = email;
        Username = username;
        Password = password;
        IsActive = isActive;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }
}
