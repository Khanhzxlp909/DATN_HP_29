package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.CategoryDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    private String Name;
    private Boolean Status;

    public Category(CategoryDTO categoryDTO) {
        this.ID = categoryDTO.getID();
        this.Name = categoryDTO.getName();
        this.Status = categoryDTO.getStatus();
    }

    public Category() {

    }

    // Getters and Setters
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

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
