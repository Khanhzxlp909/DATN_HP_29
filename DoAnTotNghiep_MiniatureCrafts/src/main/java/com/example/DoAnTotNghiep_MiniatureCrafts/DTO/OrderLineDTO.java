package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class OrderLineDTO {
    private Long ID;  // ID tự tăng
    private Long OderID;
    private VariationDTO VariationID;

    private String VariationName;
    private String Marterial;
    private Integer Quantity;
    private Double Unit_Price;
    private Double Price;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Boolean Status;

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

    public OrderLineDTO(OrderLine orderLine) {
        this.ID = orderLine.getID();
        OderID = orderLine.getOderID();
        VariationID = new VariationDTO(orderLine.getVariationID());
        VariationName = orderLine.getVariationName();
        Marterial = orderLine.getMarterial();
        Quantity = orderLine.getQuantity();
        Unit_Price = orderLine.getUnit_Price();
        Price = orderLine.getPrice();
        Creation_date = orderLine.getCreation_date();
        Edit_Date = orderLine.getEdit_Date();
        Status = orderLine.getStatus();
    }

    public OrderLineDTO(Long ID, Long oderID, VariationDTO variationID, String VariationName, String Marterial, Integer quantity, Double unit_Price, Double price, LocalDate creation_date, LocalDate edit_Date, Boolean status) {
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

    public VariationDTO getVariationID() {
        return VariationID;
    }

    public void setVariationID(VariationDTO variationID) {
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

    public OrderLineDTO() {
    }
}
