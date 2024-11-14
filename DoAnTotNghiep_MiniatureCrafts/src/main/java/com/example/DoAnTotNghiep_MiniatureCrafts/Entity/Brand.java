package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "Brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    private String Name;
    private String Note;
    private Boolean Status;

    public Brand(BrandDTO brandDTO) {
        this.ID = brandDTO.getID();
        this.Name = brandDTO.getName();
        this.Note = brandDTO.getNote();
        this.Status = brandDTO.getStatus();
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

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
