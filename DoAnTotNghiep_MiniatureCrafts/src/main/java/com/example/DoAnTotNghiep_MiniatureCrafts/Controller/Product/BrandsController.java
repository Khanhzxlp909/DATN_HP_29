package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Product;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.BrandDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandsController {

    @Autowired
    BrandsService brandsService;

    @GetMapping("get")
    public List<BrandDTO> getALL() {
        return brandsService.getALL();
    }

    @PostMapping("save")
    public Brand save(@RequestBody BrandDTO brand) {
        return brandsService.add(brand);
    }
    @PostMapping("update")
    public Brand update(@RequestBody BrandDTO brand) {
        return brandsService.update(brand);
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        brandsService.delete(id);
        return "done";
    }
}
