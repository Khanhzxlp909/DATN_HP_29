package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "OrderLine")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;  // ID tự tăng
    private Long OderID;

    @ManyToOne
    @JoinColumn(name = "VariationID")
    private Variation VariationID;
    private String VariationName;
    private String Marterial;
    private Integer Quantity;
    private Double Unit_Price;
    private Double Price;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Boolean Status;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getOderID() {
        return OderID;
    }

    public void setOderID(Long oderID) {
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

    public String getVariationName() {
        return VariationName;
    }

    public void setVariationName(String variationName) {
        VariationName = variationName;
    }

    public String getMarterial() {
        return Marterial;
    }

    public void setMarterial(String marterial) {
        Marterial = marterial;
    }

    public OrderLine(Long ID, Long oderID, Variation variationID, String VariationName, String Marterial, Integer quantity, Double unit_Price, Double price, LocalDate creation_date, LocalDate edit_Date, Boolean status) {
        this.ID = ID;
        OderID = oderID;
        VariationID = variationID;
        this.VariationName = VariationName;
        this.Marterial = Marterial;
        Quantity = quantity;
        Unit_Price = unit_Price;
        Price = price;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
        Status = status;
    }
}
