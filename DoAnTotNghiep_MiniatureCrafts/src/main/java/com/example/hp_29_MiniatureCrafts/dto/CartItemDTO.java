package com.example.hp_29_MiniatureCrafts.dto;

public class CartItemDTO {
    private Long Id;
    private CustomerDTO customer_id;
    private VariationDTO variation_id;
    private int quality;
    private int Status;

    public CartItemDTO(Long id, CustomerDTO customer_id, VariationDTO variation_id, int quality, int status) {
        Id = id;
        this.customer_id = customer_id;
        this.variation_id = variation_id;
        this.quality = quality;
        Status = status;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public CustomerDTO getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(CustomerDTO customer_id) {
        this.customer_id = customer_id;
    }

    public VariationDTO getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(VariationDTO variation_id) {
        this.variation_id = variation_id;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public CartItemDTO() {
    }
}
