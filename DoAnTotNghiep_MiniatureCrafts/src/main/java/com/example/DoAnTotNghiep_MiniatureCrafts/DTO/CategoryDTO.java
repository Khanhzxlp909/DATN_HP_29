package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class CategoryDTO {
    private int id;
    private String name;
    private boolean status;

    public CategoryDTO(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public CategoryDTO() {
    }
}
