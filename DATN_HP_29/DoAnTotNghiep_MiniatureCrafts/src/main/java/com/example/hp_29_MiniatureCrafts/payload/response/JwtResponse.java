package com.example.hp_29_MiniatureCrafts.payload.response;

import java.util.List;

public class JwtResponse {
    private final String token;
    private final Long id;
    private final String username;
    private final String email;
    private String roles;
    private final Object userInfo;  // Lưu thông tin Employee hoặc Customer

    public JwtResponse(String token, Long id, String username, String email, Object userInfo,String roles) {
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

    public Object getUserInfo() {
        return userInfo;
    }
}
