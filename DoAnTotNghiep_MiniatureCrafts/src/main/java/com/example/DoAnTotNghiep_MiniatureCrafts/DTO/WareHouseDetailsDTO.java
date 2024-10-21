package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

public class WareHouseDetailsDTO {
    private Integer ID;
    private Integer Import;
    private Integer Product;
    private Integer Quantity;
    private Double Price;
    private Double Total_Amount;
    private String Note;
    private Boolean Status;

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getImport() {
        return Import;
    }

    public void setImport(Integer imports) {
        Import = imports;
    }

    public Integer getProduct() {
        return Product;
    }

    public void setProduct(Integer product) {
        Product = product;
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

    public Double getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(Double total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
