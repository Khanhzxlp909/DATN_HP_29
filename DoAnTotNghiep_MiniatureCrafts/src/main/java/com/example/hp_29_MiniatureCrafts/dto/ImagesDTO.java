package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Images;
import com.example.hp_29_MiniatureCrafts.entity.Product;

public class ImagesDTO {
    private Integer ID;
    private ProductDTO Product;
    private String Cd_Images;
    private Boolean Set_Default;

    public ImagesDTO() {
    }

    public ImagesDTO(Integer ID, ProductDTO product, String cd_Images, Boolean set_Default) {
        this.ID = ID;
        Product = product;
        Cd_Images = cd_Images;
        Set_Default = set_Default;
    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public ProductDTO getProduct() {
        return Product;
    }

    public ImagesDTO(Images entity) {
        this.ID = entity.getID();
        this.Product = new ProductDTO(entity.getProduct());
        this.Cd_Images = entity.getCd_Images();
        this.Set_Default = entity.getSet_Default();
    }

    public void setProduct(ProductDTO product) {
        Product = product;
    }

    public String getCd_Images() {
        return Cd_Images;
    }

    public void setCd_Images(String cd_Images) {
        Cd_Images = cd_Images;
    }

    public Boolean getSet_Default() {
        return Set_Default;
    }

    public void setSet_Default(Boolean set_Default) {
        Set_Default = set_Default;
    }
}
