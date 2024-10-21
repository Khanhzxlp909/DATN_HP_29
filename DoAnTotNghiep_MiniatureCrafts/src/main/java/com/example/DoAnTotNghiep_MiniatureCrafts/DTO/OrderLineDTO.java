package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

public class OrderLineDTO {
    private Integer ProductID;
    private Integer Quantity;
    private Double Price;

    // Getters and Setters
    public Integer getProductID() {
        return ProductID;
    }

    public void setProductID(Integer productID) {
        ProductID = productID;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
