package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WareHouseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "FK_Import", referencedColumnName = "ID")
    private WareHouse wareHouse;

    @ManyToOne
    @JoinColumn(name = "FK_Product", referencedColumnName = "ID")
    private Product product;

    private int quantity;
    private double price;
    private double totalAmount;
    private String note;
    private boolean status;
}
