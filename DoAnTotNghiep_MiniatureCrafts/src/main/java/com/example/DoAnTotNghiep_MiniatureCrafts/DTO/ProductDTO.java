package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private CategoryDTO category;
}
