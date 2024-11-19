package com.example.hp_29_MiniatureCrafts.dto;

import jakarta.persistence.*;

import java.time.LocalDate;

public class EmployeeDTO {

    private Long ID;

    private String Name;

    private String Phone;

    private LocalDate Creation_date;

    private LocalDate Edit_Date;

    // setter and getter
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long ID, String name, String phone, LocalDate creation_date, LocalDate edit_Date) {
        this.ID = ID;
        Name = name;
        Phone = phone;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }
}
