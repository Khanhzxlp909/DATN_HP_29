/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.CategoryDTO;
import jakarta.persistence.*;

/**
 *
 * @author ASUS
 */
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
