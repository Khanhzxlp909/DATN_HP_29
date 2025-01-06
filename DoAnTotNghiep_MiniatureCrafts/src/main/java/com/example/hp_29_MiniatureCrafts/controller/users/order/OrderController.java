package com.example.hp_29_MiniatureCrafts.controller.users.order;


import com.example.hp_29_MiniatureCrafts.dto.OrderLineDTO;
import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.entity.OrderLine;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import com.example.hp_29_MiniatureCrafts.service.order.Voucher.VoucherService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("MiniatureCrafts")
@CrossOrigin(value = "*")

public class OrderController {
    @Autowired
    private VariationService variationService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    OrderService orderService;


    @DeleteMapping("/cancelOrder/{orderId}")
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

    @GetMapping("history/{id}")
    public List<POSOrderDTO> getAllOrder(@PathVariable("id") Long id) {

        List<POSOrderDTO> orders = orderService.getAllOrders(id);

        // Lặp qua từng đơn hàng và gắn danh sách OrderLineDTO vào đối tượng POSOrderDTO
        orders.forEach(order -> {
            // Lấy danh sách OrderLineDTO cho từng đơn hàng
            List<OrderLineDTO> orderLines = orderService.getOrdersLine(order.getID());
            // Gắn danh sách OrderLineDTO vào POSOrderDTO
            order.setOrderLine(orderLines);
        });

        return orders;

    }
    // khi order client sẽ gửi về 2 json bao gồm thông tin khách hàng,
    // là customer thông qua idCustomer được lưu trên session
    // khi đăng xuất sẽ clear session, hoặc localstore


    // lấy ra những sp có trong hóa đơn
    @GetMapping("history/getprd/{orderid}")
    public List<OrderLineDTO> getOrderHistory(@PathVariable("orderid") Long orderid) {
        // Lấy danh sách các POSOrderDTO từ service
//        List<POSOrderDTO> orders = orderService.getAllOrders(customerid);
        return orderService.getOrdersLine(orderid);
    }
    // khi bấm đặt hàng thì tạo hóa đơn r trực tiếp thêm sản phẩm đó vào hóa đơn
    // bấm đặt hàng\


    // trang đăng ký:
    // nhập thông tin
    // sau đó save vào csdl
    // tiếp tục đẩy đến trang tạo tài khoản
    // sau đó nhảy về trang order

    @PostMapping("/newOrder")
//    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody POSOrderDTO posOrderDTO) {
        try {
            // Tạo POSOrder
            POSOrder order = orderService.orderInShop(posOrderDTO);

            // Lấy ID của POSOrder vừa được lưu
            Long orderId = order.getID();
            System.out.println("ID ORDER: " + orderId);

            // Thêm danh sách OrderLine từ DTO
            for (OrderLineDTO orderLineDTO : posOrderDTO.getOrderLine()) {
                OrderLine savedOrderLine = orderService.addOrderline(orderLineDTO, orderId);
                System.out.println("variation id : " + savedOrderLine.getVariationID().getID());
                System.out.println("Saved OrderLine: " + savedOrderLine);
            }

            // Chuyển đổi POSOrder entity sang DTO để trả về
            POSOrderDTO responseDTO = orderService.mapOrderEntityToDTO(order);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException ex) {
            // Trả về lỗi nếu có exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
