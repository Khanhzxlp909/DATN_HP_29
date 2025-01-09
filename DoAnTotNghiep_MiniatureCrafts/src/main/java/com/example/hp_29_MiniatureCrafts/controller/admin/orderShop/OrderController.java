package com.example.hp_29_MiniatureCrafts.controller.admin.orderShop;

import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * API để tạo đơn hàng POS (Point of Sale)
     *
     * @param posOrderDTO Dữ liệu đơn hàng từ client
     * @return Đơn hàng đã được tạo
     */
    @PostMapping("/pos")
    public ResponseEntity<?> createOrderInPOS(@RequestBody POSOrderDTO posOrderDTO) {
        try {
            // Gọi service để xử lý đơn hàng POS
            POSOrder savedOrder = orderService.orderInPOS(posOrderDTO);
            return ResponseEntity.ok(savedOrder); // Trả về đơn hàng đã lưu
        } catch (RuntimeException e) {
            // Xử lý lỗi và trả về thông báo lỗi cho client
            return ResponseEntity.badRequest().body("Lỗi khi tạo đơn hàng POS: " + e.getMessage());
        }
    }
}
