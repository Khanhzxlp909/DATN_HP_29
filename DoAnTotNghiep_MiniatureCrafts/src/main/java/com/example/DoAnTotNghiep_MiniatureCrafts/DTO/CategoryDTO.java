package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;

public class CategoryDTO {
    private Integer ID;
    private String Name;
    private Boolean Status;

    public CategoryDTO(Category category) {
        this.ID = category.getID();
        this.Name = category.getName();
        this.Status = category.getStatus();
    }

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

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
