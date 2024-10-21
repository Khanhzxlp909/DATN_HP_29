package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariationDTO {
    private int id;
    private ProductDTO product;
    private String sku;
    private double price;
    private int quantity;
    private BrandDTO brand;
    private String material;
    private double weight;
    private boolean status;
}
