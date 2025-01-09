package com.example.hp_29_MiniatureCrafts.controller.admin.orderShop;

import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
public class POSOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/pos")
    public POSOrder createOrderInPOS(@RequestBody POSOrderDTO posOrderDTO) {
        POSOrder savedOrder = orderService.orderInPOS(posOrderDTO);
        return savedOrder; // Trả về DTO đầy đủ
    }

    @GetMapping("/pos/{id}")
    public POSOrderDTO getOrderInPOS(@PathVariable("id") Long id) {
        POSOrderDTO order = orderService.findbyID(id);
        return order;
    }
}
