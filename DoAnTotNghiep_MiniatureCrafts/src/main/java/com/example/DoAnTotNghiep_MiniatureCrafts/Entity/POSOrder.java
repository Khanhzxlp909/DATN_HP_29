package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "POSOder")
public class POSOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "InvoiceDetail")
    private Long InvoiceDetail;

    @Column(name = "CustomerID")
    private Integer CustomerID;

    @Column(name = "Creation_date")
    private LocalDate Creation_date;

    @Column(name = "Code_Voucher", length = 50)
    private String Code_Voucher;

    @Column(name = "Total_Amount", precision = 10, scale = 2)
    private BigDecimal Total_Amount;

    @Column(name = "Discount_Amount", precision = 10, scale = 2)
    private BigDecimal Discount_Amount;

    @Column(name = "Total_Payment", precision = 10, scale = 2)
    private BigDecimal Total_Payment;

    @Column(name = "PaymentMethod")
    private Integer PaymentMethod;

    @Column(name = "Note", length = 255)
    private String Note;

    @Column(name = "Status")
    private Boolean Status;

    @Column(name = "Edit_Date")
    private LocalDate Edit_Date;

    @Column(name = "Type_Oder")
    private Integer Type_Oder;

    public POSOrder() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getInvoiceDetail() {
        return InvoiceDetail;
    }

    public void setInvoiceDetail(Long invoiceDetail) {
        InvoiceDetail = invoiceDetail;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    public LocalDate getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        Creation_date = creation_date;
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

    public Integer getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public LocalDate getEdit_Date() {
        return Edit_Date;
    }

    public void setEdit_Date(LocalDate edit_Date) {
        Edit_Date = edit_Date;
    }

    public Integer getType_Oder() {
        return Type_Oder;
    }

    public void setType_Oder(Integer type_Oder) {
        Type_Oder = type_Oder;
    }

    public POSOrder(Long ID, Long invoiceDetail, Integer customerID, LocalDate creation_date, String code_Voucher, BigDecimal total_Amount, BigDecimal discount_Amount, BigDecimal total_Payment, Integer paymentMethod, String note, Boolean status, LocalDate edit_Date, Integer type_Oder) {
        this.ID = ID;
        InvoiceDetail = invoiceDetail;
        CustomerID = customerID;
        Creation_date = creation_date;
        Code_Voucher = code_Voucher;
        Total_Amount = total_Amount;
        Discount_Amount = discount_Amount;
        Total_Payment = total_Payment;
        PaymentMethod = paymentMethod;
        Note = note;
        Status = status;
        Edit_Date = edit_Date;
        Type_Oder = type_Oder;
    }
}
