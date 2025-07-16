package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Images;

public class ImagesDTO {
    private Integer ID;
    private String Model;
    private Long ProductID;
    private String Cd_Images;
    private Boolean Set_Default;

    public ImagesDTO() {
    }

    public ImagesDTO(Integer ID, String model, Long productID, String cd_Images, Boolean set_Default) {
        this.ID = ID;
        Model = model;
        ProductID = productID;
        Cd_Images = cd_Images;
        Set_Default = set_Default;
    }

    public ImagesDTO(Images entity) {
        this.ID = entity.getID();
        this.Model = entity.getModel();
        this.ProductID = entity.getEntity_ID();
        this.Cd_Images = entity.getCd_Images();
        this.Set_Default = entity.getSet_Default();
    }


    public Long getProductID() {
        return ProductID;
    }

    public void setProductID(Long productID) {
        ProductID = productID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
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
