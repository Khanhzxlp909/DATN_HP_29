package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class CustomerDTO {
    private int id;
    private String name;
    private String username;
    private String address;
    private String phone;
    private boolean status;
    private Date creationDate;
    private Date editDate;

    public CustomerDTO() {
    }

    public CustomerDTO(int id, String name, String username, String address, String phone, boolean status, Date creationDate, Date editDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.creationDate = creationDate;
        this.editDate = editDate;
    }
}
