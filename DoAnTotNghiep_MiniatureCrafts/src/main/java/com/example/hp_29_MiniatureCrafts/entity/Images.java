package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.ImagesDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false) // Đặt tên cột là Product_ID
    private Product Product;


    private String Cd_Images;
    private Boolean Set_Default;
    public Images(Integer ID, Product product, String cd_Images, Boolean set_Default) {
        this.ID = ID;
        Product = product;
        Cd_Images = cd_Images;
        Set_Default = set_Default;
    }

    public Images(ImagesDTO dto) {
        this.ID = dto.getID();
        this.Product = new Product(dto.getProduct());
        this.Cd_Images = dto.getCd_Images();
        this.Set_Default = dto.getSet_Default();
    }

    public Images() {
    }


    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
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