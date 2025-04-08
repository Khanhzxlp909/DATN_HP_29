package com.example.hp_29_MiniatureCrafts.controller.admin.product;


import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
import com.example.hp_29_MiniatureCrafts.entity.Product;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import com.example.hp_29_MiniatureCrafts.service.product.ProductService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/product")
public class productController {

    @Autowired
    ProductService productService;

    @Autowired
    VariationService variationService;


    @GetMapping("findAll")
    public List<ProductDTO> getALL() {
        return productService.getAllProduct();
    }

    @PostMapping("/saved")
    public ProductDTO saved(@RequestBody ProductDTO product) {
        return variationService.saveProduct(product);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updated(@RequestBody ProductDTO product) {
        try {
            ProductDTO updatedProduct = variationService.saveProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

}
