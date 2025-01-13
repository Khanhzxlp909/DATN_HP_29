package com.example.hp_29_MiniatureCrafts.dto;

import java.time.LocalDate;

public class WareHouseDTO {
    private Long ID;
    private String Code_Inventory;
    private EmployeeDTO Employee;
    private SupplierDTO Supplier;
    private String Note;
    private Boolean Status;
    private Double Total_Amount;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;

    public WareHouseDTO() {
    }

    public Double getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(Double total_Amount) {
        Total_Amount = total_Amount;
    }

    public WareHouseDTO(Long ID, String code_Inventory, EmployeeDTO employee, SupplierDTO supplier, String note, Boolean status, Double total_Amount, LocalDate creation_date, LocalDate edit_Date) {
        this.ID = ID;
        Code_Inventory = code_Inventory;
        Employee = employee;
        Supplier = supplier;
        Note = note;
        Status = status;
        Total_Amount = total_Amount;
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

    public String getCode_Inventory() {
        return Code_Inventory;
    }

    public void setCode_Inventory(String code_Inventory) {
        Code_Inventory = code_Inventory;
    }

    public EmployeeDTO getEmployee() {
        return Employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.Employee = employee;
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

    public SupplierDTO getSupplier() {
        return Supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.Supplier = supplier;
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
