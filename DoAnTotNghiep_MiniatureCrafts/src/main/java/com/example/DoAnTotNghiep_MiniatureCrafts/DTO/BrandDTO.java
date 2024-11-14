package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;

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


    public BrandDTO(Brand brands) {
        this.ID = brands.getID();
        this.Name = brands.getName();
        this.Note = brands.getNote();
        this.Status = brands.getStatus();
    }


    public BrandDTO() {
    }

    public BrandDTO(Long id) {
        this.ID = id;
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
