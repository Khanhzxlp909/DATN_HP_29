package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    private Long ID;
    private String Name;
    private Category CategoryID;

    public ProductDTO() {
    }

    public ProductDTO(Long ID, String name, Category categoryID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
    }

    public ProductDTO(Long id) {
        this.ID = id;
    }

    @JsonProperty("id")
    public Long getId() {
        return ID;
    }

    @JsonProperty("name")
    public String getName() {
        return Name;
    }

    @JsonProperty("categoryID")
    public Category getCategoryID() {
        return CategoryID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCategoryID(Category categoryID) {
        CategoryID = categoryID;
    }
}
