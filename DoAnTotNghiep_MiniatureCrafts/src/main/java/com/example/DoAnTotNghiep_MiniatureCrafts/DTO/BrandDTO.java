package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

public class BrandDTO {
    private Integer ID;
    private String Name;
    private String Note;
    private Boolean Status;

    public BrandDTO(Integer ID, String name, String note, Boolean status) {
        this.ID = ID;
        Name = name;
        Note = note;
        Status = status;
    }

    public BrandDTO() {
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }




    // Getters and Setters
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
