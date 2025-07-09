package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductDTO {
    private Long ID;
    private String Name;
    private CategoryDTO CategoryID;
    private BrandDTO BrandID;
    private List<ImagesDTO> images;

    public ProductDTO(Long id, String name, CategoryDTO categoryID, BrandDTO brandID, List<ImagesDTO> images) {
        this.ID = id;
        this.Name = name;
        CategoryID = categoryID;
        BrandID = brandID;
        this.images = images;
    }

    public ProductDTO(Product entity) {
        this.ID = entity.getID();
        Name = entity.getName();
        CategoryID = new CategoryDTO(entity.getCategoryID());
        BrandID = new BrandDTO(entity.getBrandID());
    }

    public ProductDTO(Product entity, List<ImagesDTO> images) {
        this.ID = entity.getID();
        this.Name = entity.getName();
        this.CategoryID = new CategoryDTO(entity.getCategoryID());
        this.BrandID = new BrandDTO(entity.getBrandID());
        this.images = images;
    }

    public ProductDTO(Long id) {
        this.ID = id;
    }

    @JsonProperty("id")
    public Long getID() {
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

    @JsonProperty("brandID")
    public BrandDTO getBrandID() {
        return BrandID;
    }

    public void setBrandID(BrandDTO brandID) {
        BrandID = brandID;
    }

    public List<ImagesDTO> getImages() {
        return images;
    }

    public void setImages(List<ImagesDTO> images) {
        this.images = images;
    }
}
