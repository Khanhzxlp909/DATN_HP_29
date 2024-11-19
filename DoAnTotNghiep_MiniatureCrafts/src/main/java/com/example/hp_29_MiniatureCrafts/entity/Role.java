package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(Integer ID, ERole name) {
        this.ID = ID;
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public Role() {
    }
}


// Getters and Setters
