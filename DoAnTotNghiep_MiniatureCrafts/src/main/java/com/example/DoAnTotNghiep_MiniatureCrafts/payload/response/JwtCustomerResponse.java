package com.example.DoAnTotNghiep_MiniatureCrafts.payload.response;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;

import java.util.List;
import java.util.Optional;

public class JwtCustomerResponse {
    private String token;
    private String type = "Bearer";
    private List<Customer> customers;
    private String username;
    private String email;
    private List<String> roles;

    public JwtCustomerResponse(String accessToken, List<Customer> customers, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.customers = customers;
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

    public List<Customer>  getId() {
        return customers;
    }

    public void setId(List<Customer>  customers) {
        this.customers = customers;
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
