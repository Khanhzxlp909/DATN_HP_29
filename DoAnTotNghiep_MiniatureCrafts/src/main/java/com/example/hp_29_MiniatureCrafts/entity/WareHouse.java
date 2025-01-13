package com.example.hp_29_MiniatureCrafts.entity;

import com.example.hp_29_MiniatureCrafts.dto.WareHouseDTO;
import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "WareHouse")
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "Code_Inventory", unique = true)
    private String Code_Inventory;

    @ManyToOne
    @JoinColumn(name = "Employee")
    private Employee Employee;

    private String Note;

    @ManyToOne
    @JoinColumn(name = "Supplier")
    private Supplier Supplier;

    private Boolean Status;

    private Double Total_Amount;

    @Column(name = "Creation_date")
    private LocalDate Creation_date;

    @Column(name = "Edit_Date")
    private LocalDate Edit_Date;

    public WareHouse() {

    }

    public WareHouse(Long ID, String code_Inventory, com.example.hp_29_MiniatureCrafts.entity.Employee employee, String note, com.example.hp_29_MiniatureCrafts.entity.Supplier supplier, Boolean status, Double total_Amount, LocalDate creation_date, LocalDate edit_Date) {
        this.ID = ID;
        Code_Inventory = code_Inventory;
        Employee = employee;
        Note = note;
        Supplier = supplier;
        Status = status;
        Total_Amount = total_Amount;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }

    public Double getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(Double total_Amount) {
        Total_Amount = total_Amount;
    }

    // Getters and Setters
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCode_Inventory() {
        return Code_Inventory;
    }

    public void setCode_Inventory(String code_Inventory) {
        Code_Inventory = code_Inventory;
    }

    public Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(Employee employee) {
        Employee = employee;
    }

    public LocalDate getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        Creation_date = creation_date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public com.example.hp_29_MiniatureCrafts.entity.Supplier getSupplier() {
        return Supplier;
    }

    public void setSupplier(Supplier supplier) {
        Supplier = supplier;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public LocalDate getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(LocalDate edit_Date) {
        Edit_Date = edit_Date;
    }
}
