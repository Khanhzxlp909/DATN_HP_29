package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "WareHouseDetails")
public class WareHouseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;  // ID tự tăng

    private Long Import;

    @ManyToOne
    @JoinColumn(name = "Variation")
    private Variation Variation;
    private Integer Quantity;
    private Double Price;
    private Double Total_Amount;
    private String Note;
    private Boolean Status;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Long getImport() {
        return Import;
    }

    public void setImport(Long anImport) {
        Import = anImport;
    }

    public com.example.hp_29_MiniatureCrafts.entity.Variation getVariation() {
        return Variation;
    }

    public void setVariation(com.example.hp_29_MiniatureCrafts.entity.Variation variation) {
        Variation = variation;
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

    public WareHouseDetails() {
    }

    public WareHouseDetails(Integer ID, Long anImport, com.example.hp_29_MiniatureCrafts.entity.Variation variation, Integer quantity, Double price, Double total_Amount, String note, Boolean status) {
        this.ID = ID;
        Import = anImport;
        Variation = variation;
        Quantity = quantity;
        Price = price;
        Total_Amount = total_Amount;
        Note = note;
        Status = status;
    }
}
