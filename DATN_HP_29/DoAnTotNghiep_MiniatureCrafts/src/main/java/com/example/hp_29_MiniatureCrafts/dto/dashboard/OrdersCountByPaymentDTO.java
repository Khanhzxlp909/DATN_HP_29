package com.example.hp_29_MiniatureCrafts.dto.dashboard;

public record OrdersCountByPaymentDTO(Long paymentMethodId, String paymentMethodName, Long count) {
}
