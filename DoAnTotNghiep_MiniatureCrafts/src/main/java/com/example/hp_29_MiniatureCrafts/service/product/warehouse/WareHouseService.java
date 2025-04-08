package com.example.hp_29_MiniatureCrafts.service.product.warehouse;


import com.example.hp_29_MiniatureCrafts.dto.EmployeeDTO;
import com.example.hp_29_MiniatureCrafts.dto.SupplierDTO;
import com.example.hp_29_MiniatureCrafts.dto.WareHouseDTO;
import com.example.hp_29_MiniatureCrafts.entity.Employee;
import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import com.example.hp_29_MiniatureCrafts.entity.WareHouse;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.WareHouseRepository;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.suppillerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WareHouseService {
    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    suppillerRepository suppillerRepository;

    public List<SupplierDTO> findAllSupplier() {
        List<Supplier> supplier = suppillerRepository.findAll();
        return supplier.stream().map(
                supplier1 -> {
                    SupplierDTO dto = mapSuppierToDTO(supplier1);

                    return dto;
                }
        ).collect(Collectors.toList());
    }

    public List<SupplierDTO> findAllSupplierByStatus() {
        List<Supplier> supplier = suppillerRepository.findAllByStatus();
        return supplier.stream().map(
                supplier1 -> {
                    SupplierDTO dto = mapSuppierToDTO(supplier1);

                    return dto;
                }
        ).collect(Collectors.toList());
    }

    public List<WareHouseDTO> findAllWareHouse() {
        List<WareHouse> wareHouses = wareHouseRepository.findAll();
        return wareHouses.stream().map(
                wareHouse -> {
                    WareHouseDTO dto = new WareHouseDTO(
                            wareHouse.getID(),
                            wareHouse.getCode_Inventory(),
                            MapEmployeetoDTO(wareHouse.getEmployee()),
                            mapSuppierToDTO(wareHouse.getSupplier()),
                            wareHouse.getNote(),
                            wareHouse.getStatus(),
                            wareHouse.getTotal_Amount(),
                            wareHouse.getCreation_date(),
                            wareHouse.getEdit_Date()
                    );
                    return dto;
                }
        ).collect(Collectors.toList());

    }

    public WareHouse saveWareHouse(WareHouseDTO dto) {
        return wareHouseRepository.save(mapWareHouseDTOtoEntity(dto));
    }

    public WareHouse updateWareHouse(WareHouseDTO dto, Long id) {
        return wareHouseRepository.save(mapWareHouseDTOtoEntity(dto));
    }

    public WareHouseDTO findByID(Long id) {
        WareHouse wareHouse = wareHouseRepository.findById(id).orElseThrow();
        WareHouseDTO dto = new WareHouseDTO(
                wareHouse.getID(),
                wareHouse.getCode_Inventory(),
                MapEmployeetoDTO(wareHouse.getEmployee()),
                mapSuppierToDTO(wareHouse.getSupplier()),
                wareHouse.getNote(),
                wareHouse.getStatus(),
                wareHouse.getTotal_Amount(),
                wareHouse.getCreation_date(),
                wareHouse.getEdit_Date()
        );
        return dto;
    }

    public String deleteWareHouse(Long id) {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WareHouse with ID " + id + " not found"));
        wareHouse.setStatus(false);
        wareHouseRepository.save(wareHouse);
        return "WareHouse with ID " + id + " has been deleted successfully.";
    }

    public static WareHouseDTO mapWareHouseToDTO(WareHouse wareHouse) {
        WareHouseDTO dto = new WareHouseDTO();
        dto.setID(wareHouse.getID());
        dto.setCode_Inventory(wareHouse.getCode_Inventory());
        dto.setEmployee(MapEmployeetoDTO(wareHouse.getEmployee()));
        dto.setSupplier(mapSuppierToDTO(wareHouse.getSupplier()));
        dto.setNote(wareHouse.getNote());
        dto.setStatus(wareHouse.getStatus());
        dto.setCreation_date(wareHouse.getCreation_date());
        dto.setEdit_Date(wareHouse.getEdit_Date());
        return dto;
    }

    public static SupplierDTO mapSuppierToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getID());
        dto.setName(supplier.getName());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        dto.setNote(supplier.getNote());
        dto.setStatus(supplier.getStatus());
        dto.setCreationDate(supplier.getCreation_date());
        dto.setEditDate(supplier.getEdit_Date());
        return dto;
    }

    public static Supplier mapSuppierDTOEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setID(dto.getId());
        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setNote(dto.getNote());
        supplier.setStatus(dto.getStatus());
        supplier.setCreation_date(dto.getCreationDate());
        supplier.setEdit_Date(dto.getEditDate());
        return supplier;
    }

    public static WareHouse mapWareHouseDTOtoEntity(WareHouseDTO dto) {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setID(dto.getID());
        wareHouse.setCode_Inventory(UUID.randomUUID().toString());
        wareHouse.setEmployee(MapEmployeeDTOtoEntity(dto.getEmployee()));
        wareHouse.setSupplier(mapSuppierDTOEntity(dto.getSupplier()));
        wareHouse.setTotal_Amount(0.0);
        wareHouse.setNote(dto.getNote());
        wareHouse.setStatus(dto.getStatus());
        wareHouse.setCreation_date(LocalDate.now());
        wareHouse.setEdit_Date(LocalDate.now());
        return wareHouse;
    }

    public static EmployeeDTO MapEmployeetoDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setID(employee.getID());
        dto.setName(employee.getName());
        dto.setPhone(employee.getPhone());
        dto.setCreation_date(employee.getCreation_date());
        dto.setEdit_Date(employee.getEdit_Date());
        return dto;
    }

    public static Employee MapEmployeeDTOtoEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setID(dto.getID());
        employee.setName(dto.getName());
        employee.setPhone(dto.getPhone());
        employee.setCreation_date(dto.getCreation_date());
        employee.setEdit_Date(dto.getEdit_Date());
        return employee;
    }


}
