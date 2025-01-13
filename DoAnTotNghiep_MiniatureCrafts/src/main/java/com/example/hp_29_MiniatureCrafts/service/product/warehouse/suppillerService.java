package com.example.hp_29_MiniatureCrafts.service.product.warehouse;

import com.example.hp_29_MiniatureCrafts.dto.SupplierDTO;
import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.suppillerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class suppillerService {

    @Autowired
    suppillerRepository suppillerRepository;

    @Autowired
    WareHouseService wareHouseService;

    public SupplierDTO saveSupplier(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setNote(dto.getNote());
        supplier.setStatus(dto.getStatus());
        supplier.setCreation_date(dto.getCreationDate());
        supplier.setEdit_Date(dto.getEditDate());
        suppillerRepository.save(supplier);
        return dto;

    }

    public SupplierDTO updateSupplier(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setID(dto.getId());
        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setNote(dto.getNote());
        supplier.setStatus(dto.getStatus());
        supplier.setCreation_date(dto.getCreationDate());
        supplier.setEdit_Date(dto.getEditDate());
        suppillerRepository.save(supplier);
        return dto;

    }

    public void deleteSupplier(Integer id) {
        Supplier supplier = suppillerRepository.findByID(id);
        supplier.setStatus(false);
        suppillerRepository.save(supplier);
    }

}
