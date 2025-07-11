package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    private String Name;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    @JsonBackReference
    private Category CategoryID;  // Liên kết với Category

    @ManyToOne
    @JoinColumn(name = "BrandID")
    @JsonBackReference
    private Brand BrandID;  // Liên kết với Category

    @OneToMany
    @JoinColumn(name = "ProductID", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Images> images;

    public Product() {
    }

    public Product(ProductDTO dto) {
        this.ID = dto.getID();
        Name = dto.getName();
        CategoryID = new Category(dto.getCategoryID());
        BrandID = new Brand(dto.getBrandID());
    }

    public Product(Long ID, String name, Category categoryID, Brand brandID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
        BrandID = brandID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Category getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Category categoryID) {
        CategoryID = categoryID;
    }

    public Brand getBrandID() {
        return BrandID;
    }

    public void setBrandID(Brand brandID) {
        BrandID = brandID;
    }
}
