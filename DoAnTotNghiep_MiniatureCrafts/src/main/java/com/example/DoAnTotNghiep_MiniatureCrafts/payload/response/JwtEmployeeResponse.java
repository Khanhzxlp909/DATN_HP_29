package com.example.DoAnTotNghiep_MiniatureCrafts.payload.response;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee;

import java.util.List;

public class JwtEmployeeResponse {
    private String token;
    private String type = "Bearer";
    private List<Employee> employee;
    private String username;
    private String email;
    private List<String> roles;

    public JwtEmployeeResponse(String accessToken, List<Employee> employee, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.employee = employee;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public List<Employee> getId() {
        return employee;
    }

    public void setId(List<Employee> employee) {
        this.employee = employee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
