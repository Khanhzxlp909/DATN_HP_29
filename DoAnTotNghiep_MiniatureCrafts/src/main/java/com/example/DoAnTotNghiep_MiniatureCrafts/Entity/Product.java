package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    private String Name;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category CategoryID;  // Liên kết với Category

    // Getters and Setters
    public Long getID() {
        return ID;
    }

    public Product() {
    }

    public Product(Long ID, String name, Category categoryID) {
        this.ID = ID;
        Name = name;
        CategoryID = categoryID;
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
}
