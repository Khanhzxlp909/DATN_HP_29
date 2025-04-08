package com.example.hp_29_MiniatureCrafts.controller.admin.warehouse;


import com.example.hp_29_MiniatureCrafts.dto.SupplierDTO;
import com.example.hp_29_MiniatureCrafts.dto.WareHouseDTO;
import com.example.hp_29_MiniatureCrafts.dto.WareHouseDetailsDTO;
import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import com.example.hp_29_MiniatureCrafts.entity.WareHouse;
import com.example.hp_29_MiniatureCrafts.entity.WareHouseDetails;
import com.example.hp_29_MiniatureCrafts.repository.product.warehouse.suppillerRepository;
import com.example.hp_29_MiniatureCrafts.service.product.warehouse.WareHouseDetailService;
import com.example.hp_29_MiniatureCrafts.service.product.warehouse.WareHouseService;
import com.example.hp_29_MiniatureCrafts.service.product.warehouse.suppillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(value = "*")
@RequestMapping("admin/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    WareHouseDetailService wareHouseDetailService;

    @Autowired
    suppillerService suppillerService;

    //crud supplier
    @GetMapping("suppiller/get")
    public List<SupplierDTO> findAllSupplier() {
        return wareHouseService.findAllSupplierByStatus();
    }

    @GetMapping("suppiller/findall")
    public List<SupplierDTO> findAllSuppliers() {
        return wareHouseService.findAllSupplier();
    }

    @PostMapping("suppiller/save")
    public SupplierDTO saveSupplier(@RequestBody SupplierDTO dto) {
        return suppillerService.saveSupplier(dto);
    }
    @PostMapping("suppiller/update")
    public SupplierDTO updateSuppiller(@RequestBody SupplierDTO dto) {
        return suppillerService.updateSupplier(dto);
    }
    @GetMapping("suppiller/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Integer id) {
        suppillerService.deleteSupplier(id);
        return "success";
    }

    @GetMapping("findall")
    public List<WareHouseDTO> findAll() {
        return wareHouseService.findAllWareHouse();
    }

    @GetMapping("result/{id}")
    public WareHouseDTO findById(@PathVariable("id") Long id) {
        return wareHouseService.findByID(id);
    }



    @PostMapping("update/{id}")
    public WareHouse update(@RequestBody WareHouseDTO wareHouseDTO, @PathVariable("id") Long id) {
        System.out.println("Received WareHouseDTO: " + wareHouseDTO);
        if (wareHouseDTO.getEmployee() == null) {
            System.out.println("Employee is null!");
        } else {
            System.out.println("Employee ID: " + wareHouseDTO.getEmployee().getID());
        }
//        wareHouseService.saveWareHouse(wareHouseDTO);
        return wareHouseService.updateWareHouse(wareHouseDTO, id);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        wareHouseService.deleteWareHouse(id);
        return "success";
    }

    @PostMapping("save")
    public WareHouse save(@RequestBody WareHouseDTO wareHouseDTO) {
        System.out.println("Received WareHouseDTO: " + wareHouseDTO);
        if (wareHouseDTO.getEmployee() == null) {
            System.out.println("Employee is null!");
        } else {
            System.out.println("Employee ID: " + wareHouseDTO.getEmployee().getID());
        }
//        wareHouseService.saveWareHouse(wareHouseDTO);
        return wareHouseService.saveWareHouse(wareHouseDTO);
    }


    @PostMapping("/{warehouse}/add/whdt")
    public String addWareHouseDetail(@PathVariable("warehouse") Long idWarehouse, @RequestBody List<WareHouseDetailsDTO> wareHouseDetailsDTO) {

        try {
            wareHouseDetailService.addWareHouseDetail(wareHouseDetailsDTO, idWarehouse);
            return "Thêm mới thành công!";
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm mới WareHouseDetails: " + e.getMessage());
            e.printStackTrace();
            return "Thêm mới thất bại!";
        }
    }

    @GetMapping("/{warehouse}/getall/whdt")
    public List<WareHouseDetailsDTO> getallWarehousedetail(@PathVariable("warehouse") Long idWarehouse) {

        return wareHouseDetailService.getall(idWarehouse);
    }



    // API Cập nhật WareHouseDetail
    @PostMapping("/{warehouse}/update/whdt/{id}")
    public WareHouseDetailsDTO updateWareHouseDetail(@PathVariable("id") Integer id, @RequestBody WareHouseDetailsDTO wareHouseDetailsDTO) {
        System.out.println("Line 108 ID:"+id);
        return wareHouseDetailService.updateWareHouseDetail(id, wareHouseDetailsDTO);
    }

    // API Xóa WareHouseDetail
    @GetMapping("{warehouse}/delete/whdt/{id}")
    public String deleteWareHouseDetail(@PathVariable("id") Integer id) {

        wareHouseDetailService.deleteWareHouseDetail(id);
        return "Xóa thành công!";
    }

}
