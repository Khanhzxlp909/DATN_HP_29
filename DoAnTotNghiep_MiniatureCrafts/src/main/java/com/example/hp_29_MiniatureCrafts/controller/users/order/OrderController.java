package com.example.hp_29_MiniatureCrafts.controller.users.order;


import com.example.hp_29_MiniatureCrafts.dto.OrderLineDTO;
import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.entity.OrderLine;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import com.example.hp_29_MiniatureCrafts.service.order.Voucher.VoucherService;
import com.example.hp_29_MiniatureCrafts.service.product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("history/{id}")
    public Page<POSOrderDTO> getAllOrderByCustomer(Pageable pageable, @PathVariable("id") Long id) {
        // Lấy danh sách đơn hàng theo khách hàng
        Page<POSOrderDTO> orders = orderService.getAllOrdersbyCustomer(pageable, id);

        // Duyệt qua từng đơn hàng và gắn thêm thuộc tính "statusText"
        orders.forEach(order -> {
            // Lấy danh sách OrderLineDTO cho từng đơn hàng
            List<OrderLineDTO> orderLines = orderService.getOrdersLine(order.getID());
            order.setOrderLine(orderLines);

            // Chuyển đổi status thành chuỗi và gán vào DTO
            order.setStatusText(mapStatusToText(order.getStatus()));
        });

        return orders; // Trả về danh sách đã có statusText
    }

    private String mapStatusToText(int status) {
        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(0, "Huỷ đơn");
        statusMap.put(1, "Chờ xác nhận");
        statusMap.put(2, "Đã xác nhận");
        statusMap.put(3, "Đang giao hàng");
        statusMap.put(4, "Đã giao hàng thành công");

        return statusMap.getOrDefault(status, "Không xác định"); // Mặc định nếu không có trong danh sách
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
            System.out.println("totalamount : " + posOrderDTO.getTotal_Amount());
            POSOrder order = orderService.orderInShop(posOrderDTO);

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
