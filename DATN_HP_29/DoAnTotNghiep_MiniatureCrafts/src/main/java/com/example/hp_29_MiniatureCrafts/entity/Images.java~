package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.ImagesDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng
    private String Model;
    private Long ProductID;
    private String Cd_Images;
    private Boolean Set_Default;


    public Images(Integer ID, String model, Long productID, String cd_Images, Boolean set_Default) {
        this.ID = ID;
        Model = model;
        ProductID = productID;
        Cd_Images = cd_Images;
        Set_Default = set_Default;
    }


    public Images(ImagesDTO dto) {
        this.ID = dto.getID();
        this.Model = dto.getModel();
        this.ProductID = dto.getProductID();
        this.Cd_Images = dto.getCd_Images();
        this.Set_Default = dto.getSet_Default();
    }

    public Images() {
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

    public Long getProductID() {
        return ProductID;
    }

    public void setProductID(Long productID) {
        ProductID = productID;
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
