package com.example.hp_29_MiniatureCrafts.dto;

import lombok.Getter;
import lombok.Setter;


public class ContactRequest {

    private Integer Id;
    private String fullName;
    private String email;
    private String phone;
    private String content;

    public ContactRequest() {
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public ContactRequest(Integer id, String fullName, String email, String phone, String content) {
        Id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContactRequest(String fullName, String email, String phone, String content) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.content = content;
    }
}
