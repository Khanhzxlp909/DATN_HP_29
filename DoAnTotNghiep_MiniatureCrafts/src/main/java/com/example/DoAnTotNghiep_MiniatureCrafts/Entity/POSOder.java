package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "POSOder")
@NoArgsConstructor
@AllArgsConstructor
public class POSOder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_Customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "FK_User")
    private Users user;

    @Column(nullable = false, unique = true)
    private UUID codeVoucher;

    private Double totalAmount;

    private Double discountAmount;

    private Double totalPayment;

    @ManyToOne
    @JoinColumn(name = "FK_PaymentMethod")
    private PaymentMethod paymentMethod;

    private String note;

    private Boolean status;

    private Date creationDate;

    private Date editDate;

    private Integer typeOrder;
}
