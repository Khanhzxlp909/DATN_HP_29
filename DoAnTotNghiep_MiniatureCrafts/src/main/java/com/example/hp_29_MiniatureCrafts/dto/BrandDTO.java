package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Brand;

public class BrandDTO {
    private Long ID;
    private String Name;
    private String Note;
    private Boolean Status;


    public BrandDTO(Long ID, String name, String note, Boolean status) {
        this.ID = ID;
        Name = name;
        Note = note;
        Status = status;
    }

    public BrandDTO() {
    }

    public BrandDTO(Brand brand) {
        this.ID = brand.getID();
        this.Name = brand.getName();
        this.Note = brand.getNote();
        this.Status = brand.getStatus();

    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
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
