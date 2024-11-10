package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class POSOrderDTO {
    private Long ID;
    private Long InvoiceDetail;
    private Integer CustomerID;
    private String Code_Voucher;
    private BigDecimal Total_Amount;
    private BigDecimal Discount_Amount;
    private BigDecimal Total_Payment;
    private Integer PaymentMethod;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Integer Type_Oder;
    private String Note;
    private Boolean Status;

}
