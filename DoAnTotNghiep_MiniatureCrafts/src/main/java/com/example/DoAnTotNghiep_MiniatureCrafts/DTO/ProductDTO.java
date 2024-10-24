package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    private Integer ID;
    private String Name;
    private Category CategoryID;

    public ProductDTO() {
    }

    public ProductDTO(Integer ID, String name, Category categoryID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
    }

    @JsonProperty("id")
    public Integer getId() {
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

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCategoryID(Category categoryID) {
        CategoryID = categoryID;
    }
}
