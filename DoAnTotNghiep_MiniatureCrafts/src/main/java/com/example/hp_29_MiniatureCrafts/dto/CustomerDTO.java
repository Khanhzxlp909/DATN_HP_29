package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Customer;

import java.util.Date;

public class CustomerDTO {
    private Long ID;
    private String Name;
    private String Address;
    private String Phone;
    private String Note;

    private Boolean Status;
    private Date Creation_date;
    private Date Edit_Date;

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public CustomerDTO(Long ID, String name, String address, String phone, String note, Boolean status, Date creation_date, Date edit_Date) {
        this.ID = ID;
        Name = name;
        Address = address;
        Phone = phone;
        Note = note;
        Status = status;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }

    public CustomerDTO(Customer customerID) {
        this.ID = customerID.getID();
        this.Name = customerID.getName();
        this.Address = customerID.getAddress();
        this.Phone = customerID.getPhone();
        this.Note = customerID.getNote();
        this.Status = customerID.getStatus();
        this.Creation_date = customerID.getCreation_date();
        this.Edit_Date = customerID.getEdit_Date();

    }

    public CustomerDTO() {

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Date getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(Date creation_date) {
        Creation_date = creation_date;
    }

    public Date getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(Date edit_Date) {
        Edit_Date = edit_Date;
    }
}
