package com.example.hp_29_MiniatureCrafts.dto;

public class ReturnRequestDTO {
    private Long orderLineId;
    private Long customerId;
    private String reason;

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReturnRequestDTO(Long orderLineId, Long customerId, String reason) {
        this.orderLineId = orderLineId;
        this.customerId = customerId;
        this.reason = reason;
    }

    public ReturnRequestDTO() {
    }
}
