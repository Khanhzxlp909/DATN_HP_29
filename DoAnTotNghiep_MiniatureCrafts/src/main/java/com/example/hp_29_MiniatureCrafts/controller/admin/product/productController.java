package com.example.hp_29_MiniatureCrafts.controller.admin.product;


import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/product")
public class productController {

    @Autowired
    VariationService variationService;

    @PostMapping("/saved")
    public ProductDTO saved(@RequestBody ProductDTO product) {
        return variationService.saveProduct(product);
    }

    @PostMapping("/update")
    public ProductDTO updated(@RequestBody ProductDTO product) {
        return variationService.saveProduct(product);
    }
}
