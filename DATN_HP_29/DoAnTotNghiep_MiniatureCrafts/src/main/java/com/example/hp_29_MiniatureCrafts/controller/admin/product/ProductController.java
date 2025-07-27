package com.example.hp_29_MiniatureCrafts.controller.admin.product;


import com.example.hp_29_MiniatureCrafts.dto.ProductDTO;
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
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    VariationService variationService;

    @GetMapping("getproduct")
    public List<ProductDTO> getProductDTOS() {
        return variationService.getProducts();
    }

    @GetMapping("findAll")
    public List<ProductDTO> getALL() {
        return productService.getAllProduct();
    }

    @GetMapping("findByID/{id}")
    public ProductDTO getProductByID(@PathVariable Long id) {
        return productService.getProductByID(id);
    }

    @PostMapping("saved")
    public ProductDTO saved(@RequestBody ProductDTO product) {
        System.out.println("🔍 [API] Received product for saving: " + product);
        try {
            System.out.println("🔍 [API] Saving product: " + product);
            ProductDTO savedProduct = variationService.saveProduct(product);
            System.out.println("✅ [API] Product saved successfully: " + savedProduct);
            return savedProduct;
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            throw new RuntimeException("Error saving product: " + e.getMessage());
        }
    }

    @PostMapping("update")
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
