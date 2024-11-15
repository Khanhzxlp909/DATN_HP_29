package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "OrderLine")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    @ManyToOne
    @JoinColumn( name ="OderID")
    private POSOrder OderID;

    @ManyToOne
    @JoinColumn(name="VariationID")
    private Variation VariationID;

    private Integer Quantity;
    private Double Unit_Price;
    private Double Price;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Boolean Status;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public POSOrder getOderID() {
        return OderID;
    }

    public void setOderID(POSOrder oderID) {
        OderID = oderID;
    }

    public Variation getVariationID() {
        return VariationID;
    }

    public void setVariationID(Variation variationID) {
        VariationID = variationID;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Double getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(Double unit_Price) {
        Unit_Price = unit_Price;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
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

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public OrderLine() {
    }

    public OrderLine(Integer ID, POSOrder oderID, Variation variationID, Integer quantity, Double unit_Price, Double price, LocalDate creation_date, LocalDate edit_Date, Boolean status) {
        this.ID = ID;
        OderID = oderID;
        VariationID = variationID;
        Quantity = quantity;
        Unit_Price = unit_Price;
        Price = price;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
        Status = status;
    }
}
