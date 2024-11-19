package com.example.hp_29_MiniatureCrafts.controller.admin.warehouse;


import com.example.hp_29_MiniatureCrafts.dto.WareHouseDTO;
import com.example.hp_29_MiniatureCrafts.dto.WareHouseDetailsDTO;
import com.example.hp_29_MiniatureCrafts.entity.WareHouse;
import com.example.hp_29_MiniatureCrafts.entity.WareHouseDetails;
import com.example.hp_29_MiniatureCrafts.service.product.warehouse.WareHouseDetailService;
import com.example.hp_29_MiniatureCrafts.service.product.warehouse.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("admin/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    WareHouseDetailService wareHouseDetailService;

    @GetMapping("findall")
    public List<WareHouseDTO> findAll() {
        return wareHouseService.findAllWareHouse();
    }

    @GetMapping("result/{id}")
    public List<WareHouseDTO> findById(@PathVariable("id") Long id) {
        return wareHouseService.findByID(id);
    }


    // sau khi tạo ware house thì bên sever gửi lên 1 json body, bên fontend sẽ lưu vào localstore dưới 1 pakagejson tên là warehouse
    // khi add warehouse detail sẽ lấy id ở trong warehouse ra r dùng làm cầu nối


//    @PostMapping("result/{id}/save")
//    public WareHouseDetails saveDetails(@RequestBody WareHouseDetailsDTO wareHouseDetailsDTO) {
//
//
//    }


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
    public WareHouseDetails addWareHouseDetail(@PathVariable("warehouse") Long idWarehouse, @RequestBody WareHouseDetailsDTO wareHouseDetailsDTO) {
        if (wareHouseDetailsDTO.getVariation() == null) {
            throw new RuntimeException("Variation không thể là null!");
        }
        System.out.println("Line 85:  Price: "+wareHouseDetailsDTO.getPrice());;
        System.out.println("Received wareHouseDetailsDTO: " + wareHouseDetailsDTO.getVariation().getID());
        System.out.println("ID WAREHOUSE: " + idWarehouse);
        return wareHouseDetailService.addWareHouseDetail(wareHouseDetailsDTO);
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
