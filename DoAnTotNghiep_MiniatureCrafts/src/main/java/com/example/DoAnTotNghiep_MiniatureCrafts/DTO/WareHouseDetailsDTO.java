package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
