package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class OrderLineDTO {
    private Integer ID;  // ID tự tăng
    private POSOrderDTO OderID;
    private VariationDTO VariationID;

    private Integer Quantity;
    private Double Unit_Price;
    private Double Price;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Boolean Status;

    public OrderLineDTO(OrderLine orderLine) {
        this.ID = orderLine.getID();
        OderID = new POSOrderDTO(orderLine.getOderID());
        VariationID = new VariationDTO(orderLine.getVariationID());
        Quantity = orderLine.getQuantity();
        Unit_Price = orderLine.getUnit_Price();
        Price = orderLine.getPrice();
        Creation_date = orderLine.getCreation_date();
        Edit_Date = orderLine.getEdit_Date();
        Status = orderLine.getStatus();
    }
    public OrderLineDTO(Integer ID, POSOrderDTO oderID, VariationDTO variationID, Integer quantity, Double unit_Price, Double price, LocalDate creation_date, LocalDate edit_Date, Boolean status) {
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


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public POSOrderDTO getOderID() {
        return OderID;
    }

    public void setOderID(POSOrderDTO oderID) {
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
