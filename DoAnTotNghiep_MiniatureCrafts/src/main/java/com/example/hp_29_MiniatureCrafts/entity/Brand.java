/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.example.hp_29_MiniatureCrafts.entity;


import com.example.hp_29_MiniatureCrafts.dto.BrandDTO;
import jakarta.persistence.*;
/**
 *
 * @author Giohuok
 */
@Entity
@Table(name = "Brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

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
    public Long getID() {
        return ID;
    }

    public Brand() {
    }

    public Brand(Long ID, String name, String note, Boolean status) {
        this.ID = ID;
        Name = name;
        Note = note;
        Status = status;
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
