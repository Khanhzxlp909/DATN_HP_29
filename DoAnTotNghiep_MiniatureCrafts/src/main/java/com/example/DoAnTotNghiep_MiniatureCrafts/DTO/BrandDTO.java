package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

public class BrandDTO {
    private Integer ID;
    private String Name;
    private Boolean Status;

    public BrandDTO() {
    }

    public BrandDTO(Integer ID, String name, Boolean status) {
        this.ID = ID;
        Name = name;
        Status = status;
    }

    public BrandDTO(Integer id, String name, String note, Boolean status) {
        this.ID = ID;
        Name = name;
        Status = status;
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
