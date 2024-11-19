package com.example.hp_29_MiniatureCrafts.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private String roles;
    private List<?> userInfo;  // Lưu thông tin Employee hoặc Customer

    public JwtResponse(String token, Long id, String username, String email, List<?> userInfo,String roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.userInfo = userInfo;
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<?> getUserInfo() {
        return userInfo;
    }
}
