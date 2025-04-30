package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.POSOrder;

import java.time.LocalDateTime;
import java.util.List;

public class POSOrderDTO {
    private Long ID;
    private CustomerDTO customerID;
    private String Address;
    private String Code_Voucher;
    private String Total_Amount;
    private String Discount_Amount;
    private String Total_Payment;
    private PaymentMethodDTO PaymentMethod;
    private LocalDateTime Order_Time;
    private LocalDateTime Payment_Time;
    private Integer Type_Oder;
    private String Note;
    private Integer Status;
    private List<OrderLineDTO> orderLine;
    private String statusText; // Trạng thái dạng chuỗi

    public POSOrderDTO(POSOrder oder) {
        this.ID = oder.getID();
        this.customerID = new CustomerDTO(oder.getCustomerID());
        this.Address = oder.getAddress();
        Code_Voucher = oder.getCode_Voucher();
        Total_Amount = oder.getTotal_Amount().toString();
        Discount_Amount = oder.getDiscount_Amount().toString();
        Total_Payment = oder.getTotal_Payment().toString();
        PaymentMethod = new PaymentMethodDTO(oder.getPaymentMethod());
        Order_Time = oder.getOrder_Time();
        Payment_Time = oder.getPayment_Time();
        Type_Oder = oder.getType_Oder();
        Note = oder.getNote();
        Status = oder.getStatus();
    }

    public POSOrderDTO(Long ID, CustomerDTO customerID, String address, String code_Voucher, String total_Amount, String discount_Amount, String total_Payment, PaymentMethodDTO paymentMethod, LocalDateTime orderTime, LocalDateTime edit_Date, Integer type_Oder, String note, Integer status, List<OrderLineDTO> orderLine, String statusText) {
        this.ID = ID;
        this.customerID = customerID;
        Address = address;
        Code_Voucher = code_Voucher;
        Total_Amount = total_Amount;
        Discount_Amount = discount_Amount;
        Total_Payment = total_Payment;
        PaymentMethod = paymentMethod;
        Order_Time = orderTime;
        Payment_Time = edit_Date;
        Type_Oder = type_Oder;
        Note = note;
        Status = status;
        this.orderLine = orderLine;
        this.statusText = statusText;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getDiscount_Amount() {
        return Discount_Amount;
    }

    public void setDiscount_Amount(String discount_Amount) {
        Discount_Amount = discount_Amount;
    }

    public String getTotal_Payment() {
        return Total_Payment;
    }

    public void setTotal_Payment(String total_Payment) {
        Total_Payment = total_Payment;
    }

    public PaymentMethodDTO getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public LocalDateTime getOrder_Time() {
        return Order_Time;
    }

    public void setOrder_Time(LocalDateTime order_Time) {
        Order_Time = order_Time;
    }

    public LocalDateTime getPayment_Time() {
        return Payment_Time;
    }

    public void setPayment_Time(LocalDateTime payment_Time) {
        Payment_Time = payment_Time;
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

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public List<OrderLineDTO> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderLineDTO> orderLine) {
        this.orderLine = orderLine;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }

    public POSOrderDTO() {
    }


}
