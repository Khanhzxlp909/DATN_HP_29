package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Variation;

import java.util.List;

public class VariationDTO {
    private Long ID;
    private ProductDTO ProductID;
    private String Name;
    private String SKU;
    private Double Price;
    private Integer Quantity;
    private String Color;
    private String Material;
    private String Size;
    private Boolean Status;
    private String Description;
    private Integer Sold;
    private ImagesDTO images;

    public VariationDTO() {
    }

    public VariationDTO(Variation variationID) {
        this.ID = variationID.getID();
        ProductID = new ProductDTO(variationID.getProductID());
        this.Name = variationID.getName();
        this.SKU = variationID.getSKU();
        Price = variationID.getPrice();
        Quantity = variationID.getQuantity();
        Material = variationID.getMaterial();
        Size = variationID.getSize();
        Status = variationID.getStatus();
        Description = variationID.getDescription();
        this.Sold = variationID.getSold();
    }


    public VariationDTO(Long ID, ProductDTO productID, String name, String SKU, Double price, Integer quantity, String color, String material, String size, String description, Integer sold, Boolean status, ImagesDTO imagesDTO) {
        this.ID = ID;
        ProductID = productID;
        Name = name;
        this.SKU = SKU;
        Price = price;
        Quantity = quantity;
        Color = color;
        Material = material;
        Size = size;
        Description = description;
        Sold = sold;
        images = imagesDTO;
        Status = status;
    }

    public ImagesDTO getImagesDTO() {
        return images;
    }

    public void setImagesDTO(ImagesDTO imagesDTO) {
        images = imagesDTO;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public ProductDTO getProductID() {
        return ProductID;
    }

    public void setProductID(ProductDTO productID) {
        ProductID = productID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getSold() {
        return Sold;
    }

    public void setSold(Integer sold) {
        Sold = sold;
    }
}
