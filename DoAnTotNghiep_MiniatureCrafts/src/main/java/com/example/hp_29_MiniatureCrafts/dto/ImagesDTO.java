package com.example.hp_29_MiniatureCrafts.dto;

public class ImagesDTO {
    private Integer ID;
    private Integer Product;
    private String Cd_Images;
    private Boolean Set_Default;

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getProduct() {
        return Product;
    }

    public void setProduct(Integer product) {
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
