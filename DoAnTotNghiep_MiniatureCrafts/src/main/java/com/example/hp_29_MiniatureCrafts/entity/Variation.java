package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Variation")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product ProductID;  // Liên kết với Product

    private String SKU;
    private Double Price;
    private Integer Quantity;


    @ManyToOne
    @JoinColumn (name = "BrandID")
    private Brand BrandID;  // Liên kết với Brand

    private String Material;
    private Double Weight;
    private Boolean Status;



    public Variation(Long ID, Product productID, String SKU, Double price, Integer quantity, Brand brandID, String material, Double weight, Boolean status) {
        this.ID = ID;
        ProductID = productID;
        this.SKU = SKU;
        Price = price;
        Quantity = quantity;
        BrandID = brandID;
        Material = material;
        Weight = weight;
        Status = status;
    }

    public Variation() {

    }

    // Getters and Setters
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Product getProductID() {
        return ProductID;
    }

    public void setProductID(Product productID) {
        ProductID = productID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Brand getBrandID() {
        return  BrandID;
    }

    public void setBrandID(Brand brandID) {
        BrandID = brandID;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
