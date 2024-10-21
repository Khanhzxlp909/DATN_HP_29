package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class POSOderDTO {
    private int id;
    private CustomerDTO customer;
    private UsersDTO user;
    private PaymentMethodDTO paymentMethod;
    private double totalAmount;
    private double discountAmount;
    private double totalPayment;
    private int typeOder;
    private boolean status;
}
