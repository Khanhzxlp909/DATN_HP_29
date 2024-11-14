package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

public class CategoryDTO {
    private Long ID;
    private String Name;
    private Boolean Status;

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
