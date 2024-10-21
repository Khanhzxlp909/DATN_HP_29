package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POSOrder")
public class POSOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    @Column(name = "Code_Order", unique = true)
    private String Code_Order;  // Đảm bảo Code_Order là duy nhất

    @Column(name = "UserID")
    private Integer UserID;

    private Double TotalAmount;
    private Boolean Status;

    @Column(name = "Creation_date")
    @Temporal(TemporalType.DATE)
    private Date Creation_date;

    @Column(name = "Edit_Date")
    @Temporal(TemporalType.DATE)
    private Date Edit_Date;

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCode_Order() {
        return Code_Order;
    }

    public void setCode_Order(String code_Order) {
        Code_Order = code_Order;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
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
