package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.VariationDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Variation")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    @JsonBackReference
    private Product ProductID;

    private String  Name;  // Liên kết với Product

    private String SKU;
    private Double Price;
    private Integer Quantity;
    private String Color;
    private String Material;
    private String Size;
    private String Description;
    private Integer Sold;
    private Boolean Status;


    public Variation() {
    }

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

    public Variation(Long ID, Product productID, String name, String SKU, Double price, Integer quantity, String color, String material, String size,  String description, Integer sold, Boolean status) {
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
        Status = status;
    }

    public Variation(VariationDTO dto) {
        this.ID = dto.getID();
        ProductID = new Product(dto.getProductID());
        Name = dto.getName();
        this.SKU = dto.getSKU();
        Price = dto.getPrice();
        Quantity = dto.getQuantity();
        Color = dto.getColor();
        Material = dto.getMaterial();
        Size = dto.getSize();
        Description = dto.getDescription();
        Sold = dto.getSold();
        Status = dto.getStatus();
    }
}
