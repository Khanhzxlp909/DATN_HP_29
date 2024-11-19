package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng

    private String Name;
    private String Address;
    private String Phone;



    private String Note;
    private Boolean Status;

    @Column(name = "Creation_date")
    @Temporal(TemporalType.DATE)
    private Date Creation_date;

    @Column(name = "Edit_Date")
    @Temporal(TemporalType.DATE)
    private Date Edit_Date;

    public Customer() {
    }

    public Customer(Long ID, String name, String address, String phone, String note, Boolean status, Date creation_date, Date edit_Date) {
        this.ID = ID;
        Name = name;
        Address = address;
        Phone = phone;
        Note = note;
        Status = status;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
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
    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
