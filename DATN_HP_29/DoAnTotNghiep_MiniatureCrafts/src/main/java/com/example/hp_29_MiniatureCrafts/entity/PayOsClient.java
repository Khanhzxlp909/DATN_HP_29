package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PayOsClient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayOsClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // Sửa lỗi chính tả và quy ước đặt tên
    @OneToOne
    @JoinColumn(name = "OderID", referencedColumnName = "ID")
    private POSOrder orderId;

    // Sửa lỗi tràn số: long > int
    @Column(name = "OrderCode")
    private Long orderCode;

    @Column(name = "Amount")
    private Integer amount;

    @Column(name = "Status")
    private String status;

    @Column(name = "CheckoutUrl", length = 2048) // Tăng độ dài cho URL
    private String checkoutUrl;

    @Column(name = "CreationDate")
    private LocalDateTime creationDate;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    // Sửa quy ước đặt tên
    @Column(name = "Description") // Annotation giữ nguyên để khớp với DB
    private String description;

    // Gợi ý: Nếu bạn có Entity 'Order', hãy dùng quan hệ @ManyToOne
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "OderID", referencedColumnName = "id")
    // private Order order;
}