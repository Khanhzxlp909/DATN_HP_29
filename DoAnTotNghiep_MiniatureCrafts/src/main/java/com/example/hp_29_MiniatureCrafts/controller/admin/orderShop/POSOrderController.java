package com.example.hp_29_MiniatureCrafts.controller.admin.orderShop;

import com.example.hp_29_MiniatureCrafts.dto.OrderLineDTO;
import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.dto.VoucherDTO;
import com.example.hp_29_MiniatureCrafts.entity.OrderLine;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import com.example.hp_29_MiniatureCrafts.service.order.Voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
public class POSOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private VoucherService voucherService;

    @GetMapping("resultvoucher/{voucher}")
    public VoucherDTO findByVoucher(@PathVariable("voucher") String voucher) {
        return voucherService.findVoucherByCodeVoucher(voucher);
    }

    @GetMapping("history/getprd/{orderid}")
    public List<OrderLineDTO> getOrderHistory(@PathVariable("orderid") Long orderid) {
        // Lấy danh sách các POSOrderDTO từ service
//        List<POSOrderDTO> orders = orderService.getAllOrders(customerid);
        return orderService.getOrdersLine(orderid);
    }

    @GetMapping("/cancelOrder/{orderId}")
    @Transactional
    public String cancelOrder(@PathVariable Long orderId) {
        try {
            // Gọi service để hủy đơn hàng
            orderService.cancelOrder(orderId);
            return "huỷ thành công";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @GetMapping("/sucressorder/{orderId}")
    @Transactional
    public String sucressorder(@PathVariable Long orderId) {
        try {
            // Gọi service để hủy đơn hàng
            orderService.successOrder(orderId);
            return "huỷ thành công";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @GetMapping("history/{id}")
    public Page<POSOrderDTO> getAllOrderByCustomer(Pageable pageable, @PathVariable("id") Long id) {

        Page<POSOrderDTO> orders = orderService.getAllOrdersbyCustomer(pageable, id);

        // Lặp qua từng đơn hàng và gắn danh sách OrderLineDTO vào đối tượng POSOrderDTO
        orders.forEach(order -> {
            // Lấy danh sách OrderLineDTO cho từng đơn hàng
            List<OrderLineDTO> orderLines = orderService.getOrdersLine(order.getID());
            // Gắn danh sách OrderLineDTO vào POSOrderDTO
            order.setOrderLine(orderLines);
        });

        return orders;

    }

    @GetMapping("findall")
    public Page<POSOrderDTO> getAllOrder(Pageable pageable) {
        Page<POSOrderDTO> orders = orderService.findAllOrder(pageable);
        return orders;

    }

    @PostMapping("/newOrder")
//    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody POSOrderDTO posOrderDTO) {
        try {
            // Tạo POSOrder
            System.out.println("totalamount : " + posOrderDTO.getTotal_Amount());

            POSOrder order = orderService.orderInPOS(posOrderDTO);

            // Lấy ID của POSOrder vừa được lưu
            Long orderId = order.getID();
            System.out.println("ID ORDER: " + orderId);

            // Chuyển đổi POSOrder entity sang DTO để trả về
            POSOrderDTO responseDTO = orderService.mapOrderEntityToDTO(order);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (RuntimeException ex) {
            // Trả về lỗi nếu có exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }

    }
}
