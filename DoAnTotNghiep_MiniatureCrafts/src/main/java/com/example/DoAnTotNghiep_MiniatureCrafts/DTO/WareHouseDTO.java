package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
