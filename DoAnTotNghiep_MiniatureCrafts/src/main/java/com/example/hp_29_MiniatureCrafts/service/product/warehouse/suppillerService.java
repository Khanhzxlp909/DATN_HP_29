package com.example.hp_29_MiniatureCrafts.service.product.warehouse;

import com.example.hp_29_MiniatureCrafts.dto.SupplierDTO;
import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.suppillerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        supplier.setCreation_date(LocalDate.now());
        supplier.setEdit_Date(dto.getEditDate());
        suppillerRepository.save(supplier);
        return dto;

    }

    public SupplierDTO updateSupplier(SupplierDTO dto) {
        Supplier supplier = suppillerRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp với ID: " + dto.getId()));

        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setNote(dto.getNote());
        supplier.setStatus(dto.getStatus());
        supplier.setEdit_Date(LocalDate.now()); // Cập nhật ngày chỉnh sửa

        suppillerRepository.save(supplier);
        return dto;
    }


    public void deleteSupplier(Integer id) {
        Supplier supplier = suppillerRepository.findByID(id);
        supplier.setStatus(false);
        suppillerRepository.save(supplier);
    }

}
