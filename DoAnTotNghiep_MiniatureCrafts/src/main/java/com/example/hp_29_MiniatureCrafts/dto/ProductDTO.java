package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Category;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
    private Long ID;
    private String Name;
    private CategoryDTO CategoryID;

    public ProductDTO() {
    }

    public ProductDTO(Long ID, String name, CategoryDTO categoryID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
    }

    public ProductDTO(Product entity) {
        this.ID = entity.getID();
        Name = entity.getName();
        CategoryID = new CategoryDTO(entity.getCategoryID());
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
    public CategoryDTO getCategoryID() {
        return CategoryID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCategoryID(CategoryDTO categoryID) {
        CategoryID = categoryID;
    }
}
