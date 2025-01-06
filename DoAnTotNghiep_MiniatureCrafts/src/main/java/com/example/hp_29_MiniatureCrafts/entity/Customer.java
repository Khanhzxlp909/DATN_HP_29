package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    private LocalDate Creation_date;

    @Column(name = "Edit_Date")
    @Temporal(TemporalType.DATE)
    private LocalDate Edit_Date;

    public Customer() {
    }

    public Customer(Long ID, String name, String address, String phone, String note, Boolean status, LocalDate creation_date, LocalDate edit_Date) {
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

    public LocalDate getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        Creation_date = creation_date;
    }

    public LocalDate getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(LocalDate edit_Date) {
        Edit_Date = edit_Date;
    }
    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
