package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Supplier;

import java.time.LocalDate;

public class SupplierDTO {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String note;
    private Boolean status;
    private LocalDate creationDate;
    private LocalDate editDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    public SupplierDTO() {
    }

    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getID();
        this.name = supplier.getName();
        this.phone = supplier.getPhone();
        this.address = supplier.getAddress();
        this.note = supplier.getNote();
        this.status = supplier.getStatus();
        this.creationDate = supplier.getCreation_date();
        this.editDate = supplier.getEdit_Date();
    }

    public SupplierDTO(Long id, String name, String phone, String address, String note, Boolean status, LocalDate creationDate, LocalDate editDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.status = status;
        this.creationDate = creationDate;
        this.editDate = editDate;
    }
}