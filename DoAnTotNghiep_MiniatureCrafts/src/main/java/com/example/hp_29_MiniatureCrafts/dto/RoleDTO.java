package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class RoleDTO {
    private Integer ID;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public RoleDTO(Integer ID, ERole name) {
        this.ID = ID;
        this.name = name;
    }
}
