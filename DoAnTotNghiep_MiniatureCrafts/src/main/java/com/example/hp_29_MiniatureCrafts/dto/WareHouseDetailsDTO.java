package com.example.hp_29_MiniatureCrafts.dto;

public class WareHouseDetailsDTO {
    private Integer ID;
    private Long Import;
    private VariationDTO Variation;
    private Integer Quantity;
    private Double Price;
    private Double Total_Amount;
    private String Note;
    private Boolean Status;

    public WareHouseDetailsDTO() {
    }

    public WareHouseDetailsDTO(Integer ID, Long anImport, VariationDTO variation, Integer quantity, Double price, Double total_Amount, String note, Boolean status) {
        this.ID = ID;
        Import = anImport;
        Variation = variation;
        Quantity = quantity;
        Price = price;
        Total_Amount = total_Amount;
        Note = note;
        Status = status;
    }

    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Long getImport() {
        return Import;
    }

    public void setImport(Long imports) {
        Import = imports;
    }

    public VariationDTO getVariation() {
        return Variation;
    }

    public void setVariation(VariationDTO variationDTO) {
        Variation = variationDTO;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(Double total_Amount) {
        Total_Amount = total_Amount;
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
