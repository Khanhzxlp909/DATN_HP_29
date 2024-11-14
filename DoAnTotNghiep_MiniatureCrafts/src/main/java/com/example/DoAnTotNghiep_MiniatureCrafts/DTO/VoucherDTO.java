package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Voucher;

import java.util.Date;

public class VoucherDTO {
    private Integer ID;
    private String Code;
    private Double DiscountValue;
    private Date StartDate;
    private Date EndDate;
    private Boolean TypeVoucher;
    private Boolean Status;
    private Date Creation_date;
    private Date Edit_Date;


    public VoucherDTO(Integer ID, String code, Double discountValue, Date startDate, Date endDate, Boolean typeVoucher, Boolean status, Date creation_date, Date edit_Date) {
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

    public VoucherDTO() {
    }

    public VoucherDTO(Voucher voucher) {
        this.ID = voucher.getID();
        Code = voucher.getCode();
        DiscountValue = voucher.getDiscountValue();
        StartDate = voucher.getStartDate();
        EndDate = voucher.getEndDate();
        TypeVoucher = voucher.getTypeVoucher();
        Status = voucher.getStatus();
        Creation_date = voucher.getCreation_date();
        Edit_Date = voucher.getEdit_Date();
    }

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
