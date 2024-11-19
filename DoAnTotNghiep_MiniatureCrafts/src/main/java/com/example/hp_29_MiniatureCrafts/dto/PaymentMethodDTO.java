package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.PaymentMethod;

public class PaymentMethodDTO {
    private Integer ID;
    private String Type;
    private String Note;
    private Boolean Status;

    public PaymentMethodDTO(Integer ID, String type, String note, Boolean status) {
        this.ID = ID;
        Type = type;
        Note = note;
        Status = status;
    }

    public PaymentMethodDTO(PaymentMethod paymentMethod) {
        ID = paymentMethod.getID();
    }

    public PaymentMethodDTO() {

    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
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
}
