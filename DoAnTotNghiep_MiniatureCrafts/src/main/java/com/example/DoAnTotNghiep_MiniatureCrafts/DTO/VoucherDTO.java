package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDTO {
    private int id;
    private UUID code;
    private double discountValue;
    private double discountAmount;

    private Date startDate;
    private Date endDate;
    private String typeVoucher;
    private boolean status;
    private Date creationDate;
    private Date editDate;
}
