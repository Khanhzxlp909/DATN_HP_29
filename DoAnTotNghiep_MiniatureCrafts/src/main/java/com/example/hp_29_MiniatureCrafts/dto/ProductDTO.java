package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Category;
import com.example.hp_29_MiniatureCrafts.entity.Images;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    private Long ID;
    private String Name;
    private CategoryDTO CategoryID;
    private List<ImagesDTO> imagesDTOS;

    public ProductDTO(Long id, String name,CategoryDTO categoryID, List<ImagesDTO> imagesDTOS) {
        this.ID= id;
        this.Name= name;
        CategoryID = categoryID;
        this.imagesDTOS = imagesDTOS;
    }

    public Long getID() {
        return ID;
    }

    public List<ImagesDTO> getImagesDTOS() {
        return imagesDTOS;
    }

    public void setImagesDTOS(List<ImagesDTO> imagesDTOS) {
        this.imagesDTOS = imagesDTOS;
    }

    public ProductDTO() {
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
