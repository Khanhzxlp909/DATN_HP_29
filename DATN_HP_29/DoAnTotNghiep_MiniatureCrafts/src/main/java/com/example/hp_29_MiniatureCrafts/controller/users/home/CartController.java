package com.example.hp_29_MiniatureCrafts.controller.users.home;


import com.example.hp_29_MiniatureCrafts.dto.CartItemDTO;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
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
    CartService cartServices;

    @GetMapping("/findall/{customer_id}")
    public List<CartItemDTO> findAll(Pageable pageable, @PathVariable("customer_id") Long customer_id) {
        return cartServices.findAll(pageable, customer_id);
    }

    @GetMapping("count/{customer_id}")
    public Integer countCart(@PathVariable("customer_id") Long customer_id) {
        return cartServices.countTotalItemsByCustomerId(customer_id);
    }

    @PostMapping("/addtocart")
    public ResponseEntity<?> saveCart(@RequestBody CartItemDTO cartItemDTO) {

        try {
            return cartServices.addToCart(cartItemDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/editquantity/{cartItemId}/{newQuantity}")
    public ResponseEntity<?> editCart(@PathVariable("cartItemId") Long cartItemId ,@PathVariable("newQuantity") int newQuantity) {

        try {
            return cartServices.updateQuantity(cartItemId,newQuantity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/remove/{cartItemId}")
    public ResponseEntity<String> delete(@PathVariable("cartItemId") Long cartItemId) {
        cartServices.deleteCartItem(cartItemId);
        return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/removeall/{customer_id}")
    public ResponseEntity<String> removeAllCart(@PathVariable("customer_id") Long customer_id) {
        cartServices.clearCartByCustomer(customer_id);
        return new ResponseEntity<>("Cart item deleted successfully", HttpStatus.OK);
    }
}
