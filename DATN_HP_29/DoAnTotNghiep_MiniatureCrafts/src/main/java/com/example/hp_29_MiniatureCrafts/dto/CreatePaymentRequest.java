package com.example.hp_29_MiniatureCrafts.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private int amount;
    private String description;
    private String roles; // Vai trò của người tạo đơn hàng (admin, user, v.v.)
    private Long orderId; // ID của đơn hàng gốc trong hệ thống của bạn
}
