package com.example.hp_29_MiniatureCrafts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer_id;

    @ManyToOne
    @JoinColumn(name = "variation_id")
    @JsonBackReference
    private Variation variation_id;
    private int quantity;
    private int Status;

    public CartItem(Long id, Customer customer_id, Variation variation_id, int quantity, int status) {
        Id = id;
        this.customer_id = customer_id;
        this.variation_id = variation_id;
        this.quantity = quantity;
        Status = status;
    }

    public CartItem() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Customer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Customer customer_id) {
        this.customer_id = customer_id;
    }

    public Variation getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(Variation variation_id) {
        this.variation_id = variation_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
