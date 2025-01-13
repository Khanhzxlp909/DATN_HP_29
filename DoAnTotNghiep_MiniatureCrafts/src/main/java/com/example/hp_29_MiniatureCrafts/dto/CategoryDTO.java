package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Category;
/**
 *
 * @author ASUS
 */
public class CategoryDTO {
    private Long ID;
    private String Name;
    private Boolean Status;

    public CategoryDTO(Category category) {
        this.ID = category.getID();
        this.Name = category.getName();
        this.Status = category.getStatus();
    }

    // Getters and Setters
    public Long getID() {
        return ID;
    }

    public CategoryDTO() {
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
