package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.util.Date;

public class WareHouseDTO {
    private Integer ID;
    private String Code_Inventory;
    private Integer Staff;
    private Date Creation_date;
    private String Note;
    private Integer Supplier;
    private Boolean Status;
    private Date Edit_Date;

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCode_Inventory() {
        return Code_Inventory;
    }

    public void setCode_Inventory(String code_Inventory) {
        Code_Inventory = code_Inventory;
    }

    public Integer getStaff() {
        return Staff;
    }

    public void setStaff(Integer staff) {
        Staff = staff;
    }

    public Date getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(Date creation_date) {
        Creation_date = creation_date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Integer getSupplier() {
        return Supplier;
    }

    public void setSupplier(Integer supplier) {
        Supplier = supplier;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Date getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(Date edit_Date) {
        Edit_Date = edit_Date;
    }
}
