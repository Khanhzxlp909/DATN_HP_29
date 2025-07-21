package com.example.hp_29_MiniatureCrafts.dto;

import java.time.LocalDateTime;

public class ReturnResponseDTO {
    private Long id;
    private Long orderLineId;
    private Long customerId;
    private String reason;
    private LocalDateTime requestDate;
    private Integer status;
    private String adminNote;

    public ReturnResponseDTO() {
    }

    public ReturnResponseDTO(Long id, Long orderLineId, Long customerId, String reason, LocalDateTime requestDate, Integer status, String adminNote) {
        this.id = id;
        this.orderLineId = orderLineId;
        this.customerId = customerId;
        this.reason = reason;
        this.requestDate = requestDate;
        this.status = status;
        this.adminNote = adminNote;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getAdminNote() {
        return adminNote;
    }
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}
