package com.example.hp_29_MiniatureCrafts.controller.admin.product;


import com.example.hp_29_MiniatureCrafts.dto.BrandDTO;
import com.example.hp_29_MiniatureCrafts.entity.Brand;
import com.example.hp_29_MiniatureCrafts.service.product.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/brands")
//@CrossOrigin(value = "*")
public class BrandsController {

    @Autowired
    BrandsService brandsService;

    @GetMapping("get")
    public Page<BrandDTO> getALL(Pageable pageable) {
        return brandsService.getALLS(pageable);
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
