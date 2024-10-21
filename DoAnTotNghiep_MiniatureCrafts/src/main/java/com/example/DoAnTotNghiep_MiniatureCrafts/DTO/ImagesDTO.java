package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class ImagesDTO {
    private int id;
    private ProductDTO product;
    private String cdImages;
    private boolean setDefault;

    public ImagesDTO() {
    }

    public ImagesDTO(int id, ProductDTO product, String cdImages, boolean setDefault) {
        this.id = id;
        this.product = product;
        this.cdImages = cdImages;
        this.setDefault = setDefault;
    }
}
