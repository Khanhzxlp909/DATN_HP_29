package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class PaymentMethodDTO {
    private int id;
    private String type;
    private String note;
    private boolean status;
}
