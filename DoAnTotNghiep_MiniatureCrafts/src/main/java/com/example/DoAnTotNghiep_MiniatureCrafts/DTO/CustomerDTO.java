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
}
