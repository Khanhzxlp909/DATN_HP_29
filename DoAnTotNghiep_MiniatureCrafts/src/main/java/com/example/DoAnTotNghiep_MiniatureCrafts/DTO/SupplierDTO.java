package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class SupplierDTO {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String note;
    private boolean status;
    private Date creationDate;
    private Date editDate;
}
