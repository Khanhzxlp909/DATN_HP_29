package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.util.List;

public class POSOrderDTO {
    private Integer ID;
    private String Code_Order;
    private Integer UserID;
    private Double TotalAmount;
    private List<OrderLineDTO> OrderLines;

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

    public List<OrderLineDTO> getOrderLines() {
        return OrderLines;
    }

    public void setOrderLines(List<OrderLineDTO> orderLines) {
        OrderLines = orderLines;
    }
}
