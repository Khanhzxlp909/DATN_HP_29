package com.example.hp_29_MiniatureCrafts.dto;

import com.example.hp_29_MiniatureCrafts.entity.Variation;

import java.util.List;

public class VariationDTO {
    private Long ID;
    private ProductDTO ProductID;
    private String SKU;
    private String Price;
    private Integer Quantity;
    private BrandDTO BrandID;
    private String Material;
    private Double Weight;
    private Boolean Status;
    private String Note;
    private List<ImagesDTO> ImagesDTO;

    public VariationDTO() {
    }

    public VariationDTO(Long ID, ProductDTO productID, String SKU, String price, Integer quantity, BrandDTO brandID, String material, Double weight, Boolean status,String note,List<ImagesDTO> images) {
        this.ID = ID;
        ProductID = productID;
        this.SKU = SKU;
        Price = price;
        Quantity = quantity;
        BrandID = brandID;
        Material = material;
        Weight = weight;
        Status = status;
        Note = note;
        ImagesDTO = images;
    }

    public List<com.example.hp_29_MiniatureCrafts.dto.ImagesDTO> getImagesDTO() {
        return ImagesDTO;
    }

    public void setImagesDTO(List<com.example.hp_29_MiniatureCrafts.dto.ImagesDTO> imagesDTO) {
        ImagesDTO = imagesDTO;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public VariationDTO(Variation variationID) {
        this.ID = variationID.getID();
        ProductID = new ProductDTO(variationID.getProductID());
        this.SKU = variationID.getSKU();
        Price = variationID.getPrice().toString();
        Quantity = variationID.getQuantity();
        BrandID = new BrandDTO(variationID.getBrandID());
        Material = variationID.getMaterial();
        Weight = variationID.getWeight();
        Status = variationID.getStatus();
        Note = variationID.getNote();
    }

    // Getters and Setters
    public Long getID() {

        return ID;
    }

    public void setID(Long ID) {

        this.ID = ID;
    }

    public ProductDTO getProductID() {

        return ProductID;
    }

    public void setProductID(ProductDTO productID) {
        ProductID = productID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public BrandDTO getBrandID() {
        return BrandID;
    }

    public void setBrandID(BrandDTO brandID) {
        BrandID = brandID;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
