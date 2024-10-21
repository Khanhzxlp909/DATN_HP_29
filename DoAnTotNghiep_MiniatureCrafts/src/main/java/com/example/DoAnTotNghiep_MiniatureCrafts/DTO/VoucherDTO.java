package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.util.Date;
import java.util.UUID;

public class VoucherDTO {
    private Integer ID;
    private UUID Code;
    private Double DiscountValue;
    private Double DiscountAmount;
    private Date StartDate;
    private Date EndDate;
    private String TypeVoucher;
    private Boolean Status;
    private Date Creation_date;
    private Date Edit_Date;

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public UUID getCode() {
        return Code;
    }

    public void setCode(UUID code) {
        Code = code;
    }

    public Double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(Double discountValue) {
        DiscountValue = discountValue;
    }

    public Double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        DiscountAmount = discountAmount;
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

    public String getTypeVoucher() {
        return TypeVoucher;
    }

    public void setTypeVoucher(String typeVoucher) {
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
