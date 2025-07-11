package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "POSOder")
public class POSOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer CustomerID;

    @Column(name = "Address", length = 50)
    private String Address;

    @Column(name = "Code_Voucher", length = 50)
    private String Code_Voucher;

    @Column(name = "Total_Amount", precision = 10, scale = 2)
    private BigDecimal Total_Amount;

    @Column(name = "Discount_Amount", precision = 10, scale = 2)
    private BigDecimal Discount_Amount;

    @Column(name = "Total_Payment", precision = 10, scale = 2)
    private BigDecimal Total_Payment;

    @ManyToOne
    @JoinColumn(name = "PaymentMethod")
    private PaymentMethod PaymentMethod;

    @Column(name = "Order_Time")
    private LocalDateTime Order_Time;

    @Column(name = "Payment_Time")
    private LocalDateTime Payment_Time;

    @Column(name = "Type_Oder")
    private Integer Type_Oder;

    @Column(name = "Note", length = 255)
    private String Note;

    @Column(name = "Status")
    private Integer Status;

    public POSOrder() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public POSOrder(Long ID, Customer customerID, String address, String code_Voucher, BigDecimal total_Amount, BigDecimal discount_Amount, BigDecimal total_Payment, com.example.hp_29_MiniatureCrafts.entity.PaymentMethod paymentMethod, LocalDateTime creation_date, LocalDateTime edit_Date, Integer type_Oder, String note, Integer status) {
        this.ID = ID;
        CustomerID = customerID;
        Address = address;
        Code_Voucher = code_Voucher;
        Total_Amount = total_Amount;
        Discount_Amount = discount_Amount;
        Total_Payment = total_Payment;
        PaymentMethod = paymentMethod;
        Order_Time = creation_date;
        Payment_Time = edit_Date;
        Type_Oder = type_Oder;
        Note = note;
        Status = status;
    }

    public Customer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Customer customerID) {
        CustomerID = customerID;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalDateTime getOrder_Time() {
        return Order_Time;
    }

    public void setOrder_Time(LocalDateTime creation_date) {
        Order_Time = creation_date;
    }

    public String getCode_Voucher() {
        return Code_Voucher;
    }

    public void setCode_Voucher(String code_Voucher) {
        Code_Voucher = code_Voucher;
    }

    public BigDecimal getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(BigDecimal total_Amount) {
        Total_Amount = total_Amount;
    }

    public BigDecimal getDiscount_Amount() {
        return Discount_Amount;
    }

    public void setDiscount_Amount(BigDecimal discount_Amount) {
        Discount_Amount = discount_Amount;
    }

    public BigDecimal getTotal_Payment() {
        return Total_Payment;
    }

    public void setTotal_Payment(BigDecimal total_Payment) {
        Total_Payment = total_Payment;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public LocalDateTime getPayment_Time() {
        return Payment_Time;
    }

    public void setPayment_Time(LocalDateTime edit_Date) {
        Payment_Time = edit_Date;
    }

    public Integer getType_Oder() {
        return Type_Oder;
    }

    public void setType_Oder(Integer type_Oder) {
        Type_Oder = type_Oder;
    }


}
