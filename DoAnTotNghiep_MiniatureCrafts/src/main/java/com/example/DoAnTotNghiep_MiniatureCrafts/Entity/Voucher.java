package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    @Column(name = "Code")
    private String Code;

    private Double DiscountValue;

    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date StartDate;

    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date EndDate;

    private Boolean TypeVoucher;
    private Boolean Status;

    @Column(name = "Creation_date")
    @Temporal(TemporalType.DATE)
    private Date Creation_date;

    @Column(name = "Edit_Date")
    @Temporal(TemporalType.DATE)
    private Date Edit_Date;


    public Voucher() {
    }

    public Voucher(Integer ID, String code, Double discountValue, Date startDate, Date endDate, Boolean typeVoucher, Boolean status, Date creation_date, Date edit_Date) {
        this.ID = ID;
        Code = code;
        DiscountValue = discountValue;
        StartDate = startDate;
        EndDate = endDate;
        TypeVoucher = typeVoucher;
        Status = status;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(Double discountValue) {
        DiscountValue = discountValue;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public Boolean getTypeVoucher() {
        return TypeVoucher;
    }

    public void setTypeVoucher(Boolean typeVoucher) {
        TypeVoucher = typeVoucher;
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
