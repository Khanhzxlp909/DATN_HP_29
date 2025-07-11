package com.example.hp_29_MiniatureCrafts.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReturnRequest")
public class ReturnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OrderLineID")
    private OrderLine OrderLineID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer CustomerID;

    private String reason;

    @Column(name = "RequestDate")
    private LocalDateTime RequestDate = LocalDateTime.now();

    private Integer Status; // 0: Pending, 1: Approved, 2: Rejected, 3: Completed

    private String AdminNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderLine getOrderLineID() {
        return OrderLineID;
    }

    public void setOrderLineID(OrderLine orderLineID) {
        OrderLineID = orderLineID;
    }

    public Customer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Customer customerID) {
        CustomerID = customerID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        RequestDate = requestDate;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getAdminNote() {
        return AdminNote;
    }

    public void setAdminNote(String adminNote) {
        AdminNote = adminNote;
    }

    public ReturnRequest(Long id, OrderLine orderLineID, Customer customerID, String reason, LocalDateTime requestDate, Integer status, String adminNote) {
        this.id = id;
        OrderLineID = orderLineID;
        CustomerID = customerID;
        this.reason = reason;
        RequestDate = requestDate;
        Status = status;
        AdminNote = adminNote;
    }

    public ReturnRequest() {
    }


}

