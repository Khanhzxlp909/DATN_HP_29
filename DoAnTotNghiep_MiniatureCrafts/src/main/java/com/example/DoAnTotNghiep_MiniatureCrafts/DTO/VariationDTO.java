package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;

public class VariationDTO {
    private Integer ID;
    private ProductDTO ProductID;
    private String SKU;
    private Double Price;
    private Integer Quantity;
    private BrandDTO BrandID;
    private String Material;
    private Double Weight;
    private Boolean Status;

    public VariationDTO() {
    }

    public VariationDTO(Integer ID, ProductDTO productID, String SKU, Double price, Integer quantity, BrandDTO brandID, String material, Double weight, Boolean status) {
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

    // Getters and Setters
    public Integer getID() {

        return ID;
    }

    public void setID(Integer ID) {

        this.ID = ID;
    }

    public ProductDTO getProductID() {

        return ProductID;
    }

    public void setProductID(ProductDTO productID) {
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

    public BrandDTO getBrandID() {
        return BrandID;
    }

    public void setBrandID(BrandDTO brandID) {
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
