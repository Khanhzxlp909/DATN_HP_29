package com.example.DoAnTotNghiep_MiniatureCrafts.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codeInventory;

    @ManyToOne
    @JoinColumn(name = "FK_Staff", referencedColumnName = "ID")
    private Users staff;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private String note;

    @ManyToOne
    @JoinColumn(name = "FK_Supplier", referencedColumnName = "ID")
    private Supplier supplier;

    private boolean status;

    @Temporal(TemporalType.DATE)
    private Date editDate;
}
