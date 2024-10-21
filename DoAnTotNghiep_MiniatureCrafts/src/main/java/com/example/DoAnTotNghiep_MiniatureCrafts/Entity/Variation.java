package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "FK_Product", referencedColumnName = "ID")
    private Product product;

    private String sku;
    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "FK_Brand", referencedColumnName = "ID")
    private Brand brand;

    private String material;
    private double weight;
    private boolean status;
}
