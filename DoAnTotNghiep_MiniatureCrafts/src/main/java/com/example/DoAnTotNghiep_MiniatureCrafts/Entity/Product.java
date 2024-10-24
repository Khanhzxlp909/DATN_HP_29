package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    private String Name;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category CategoryID;  // Liên kết với Category

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
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
