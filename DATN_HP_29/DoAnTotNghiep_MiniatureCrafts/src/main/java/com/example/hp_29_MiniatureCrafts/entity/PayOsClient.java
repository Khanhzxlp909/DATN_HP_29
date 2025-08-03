package com.example.hp_29_MiniatureCrafts.model; // Thay đổi package cho phù hợp

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "PayOsClient") // Ánh xạ tới bảng có tên chính xác là "PayOsClient"
@Data
public class PayOsClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OderID") // Chú ý: Có thể bạn gõ nhầm 'OrderID' thành 'OderID'
    private Long oderId;

    @Column(name = "OrderCode")
    private Integer orderCode;

    @Column(name = "Amount")
    private Integer amount;

    @Column(name = "Status")
    private String status;

    @Column(name = "CheckoutUrl")
    private String checkoutUrl;

    @Column(name = "CreationDate")
    private LocalDateTime creationDate;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "Description")
    private String Description;
    // Nếu 'OderID' là khóa ngoại tới bảng Order, bạn nên dùng quan hệ @ManyToOne
    // @ManyToOne
    // @JoinColumn(name = "OderID", insertable=false, updatable=false)
    // private Order order;
}
