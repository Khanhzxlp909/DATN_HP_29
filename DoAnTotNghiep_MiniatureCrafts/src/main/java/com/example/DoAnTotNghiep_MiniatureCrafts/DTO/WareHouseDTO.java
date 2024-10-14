package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class WareHouseDTO {
    private int id;
    private String codeInventory;
    private UsersDTO staff;
    private Date creationDate;
    private String note;
    private SupplierDTO supplier;
    private boolean status;
    private Date editDate;
}
