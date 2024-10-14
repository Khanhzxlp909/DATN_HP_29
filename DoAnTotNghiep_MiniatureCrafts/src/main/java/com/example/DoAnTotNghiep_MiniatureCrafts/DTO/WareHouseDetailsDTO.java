package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class WareHouseDetailsDTO {
    private int id;
    private WareHouseDTO wareHouse;
    private ProductDTO product;
    private int quantity;
    private double price;
    private double totalAmount;
    private String note;
    private boolean status;
}
