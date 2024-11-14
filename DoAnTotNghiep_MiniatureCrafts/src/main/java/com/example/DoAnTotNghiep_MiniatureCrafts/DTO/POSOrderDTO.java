package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class POSOrderDTO {
    private Long ID;
    private CustomerDTO customerID;
    private String Code_Voucher;
    private BigDecimal Total_Amount;
    private BigDecimal Discount_Amount;
    private BigDecimal Total_Payment;
    private PaymentMethodDTO PaymentMethod;
    private LocalDate Creation_date;
    private LocalDate Edit_Date;
    private Integer Type_Oder;
    private String Note;
    private Boolean Status;

    public POSOrderDTO(POSOrder oder) {
        this.ID = oder.getID();
        this.customerID = new CustomerDTO(oder.getCustomerID());
        Code_Voucher = oder.getCode_Voucher();
        Total_Amount = oder.getTotal_Amount();
        Discount_Amount = oder.getDiscount_Amount();
        Total_Payment = oder.getTotal_Payment();
        PaymentMethod = new PaymentMethodDTO(oder.getPaymentMethod());
        Creation_date = oder.getCreation_date();
        Edit_Date = oder.getEdit_Date();
        Type_Oder = oder.getType_Oder();
        Note = oder.getNote();
        Status = oder.getStatus();
    }

    public POSOrderDTO(Long ID,  CustomerDTO customerID, String code_Voucher, BigDecimal total_Amount, BigDecimal discount_Amount, BigDecimal total_Payment, PaymentMethodDTO paymentMethod, LocalDate creation_date, LocalDate edit_Date, Integer type_Oder, String note, Boolean status) {
        this.ID = ID;
        this.customerID = customerID;
        Code_Voucher = code_Voucher;
        Total_Amount = total_Amount;
        Discount_Amount = discount_Amount;
        Total_Payment = total_Payment;
        PaymentMethod = paymentMethod;
        Creation_date = creation_date;
        Edit_Date = edit_Date;
        Type_Oder = type_Oder;
        Note = note;
        Status = status;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public CustomerDTO getCustomerID() {
        return customerID;
    }

    public void setCustomerID(CustomerDTO customerID) {
        this.customerID = customerID;
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

    public PaymentMethodDTO getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public LocalDate getCreation_date() {
        return Creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        Creation_date = creation_date;
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

    public POSOrderDTO() {
    }
}
