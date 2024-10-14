package com.example.DoAnTotNghiep_MiniatureCrafts.DTO;

import lombok.Data;

@Data
public class RoleDTO {
    private int id;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}
