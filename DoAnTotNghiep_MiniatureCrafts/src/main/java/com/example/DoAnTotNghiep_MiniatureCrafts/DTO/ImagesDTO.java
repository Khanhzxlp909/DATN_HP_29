package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class ImagesDTO {
    private int id;
    private ProductDTO product;
    private String cdImages;
    private boolean setDefault;
}
