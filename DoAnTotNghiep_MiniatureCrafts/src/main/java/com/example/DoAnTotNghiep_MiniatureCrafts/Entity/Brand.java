package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

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
