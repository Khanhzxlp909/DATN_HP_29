package com.example.hp_29_MiniatureCrafts.controller.users.home;


import com.example.hp_29_MiniatureCrafts.dto.CartItemDTO;
import com.example.hp_29_MiniatureCrafts.service.order.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("findall/{customer_id}")
    public Page<CartItemDTO> findAll(Pageable pageable, @PathVariable("customer_id") Long customer_id) {
        return cartService.findAll(pageable, customer_id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCart(@RequestBody List<CartItemDTO> cartItemDTOs) {
        try {
            cartService.save(cartItemDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cart items saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving cart items: " + e.getMessage());
        }
    }


}
