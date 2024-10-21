package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    private Boolean CanCreate;
    private Boolean CanUpdate;
    private Boolean CanDelete;
    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Boolean getCanCreate() {
        return CanCreate;
    }

    public void setCanCreate(Boolean canCreate) {
        CanCreate = canCreate;
    }

    public Boolean getCanUpdate() {
        return CanUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        CanUpdate = canUpdate;
    }

    public Boolean getCanDelete() {
        return CanDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        CanDelete = canDelete;
    }
}


// Getters and Setters
