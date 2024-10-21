package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.util.List;

public class ProductDTO {
    private Integer ID;
    private String Name;
    private String Code;
    private Double Price;
    private Boolean Status;
    private List<String> ImageURLs;

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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public List<String> getImageURLs() {
        return ImageURLs;
    }

    public void setImageURLs(List<String> imageURLs) {
        ImageURLs = imageURLs;
    }
}
