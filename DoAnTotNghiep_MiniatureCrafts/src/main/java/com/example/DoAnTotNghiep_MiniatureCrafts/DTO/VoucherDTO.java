package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
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
