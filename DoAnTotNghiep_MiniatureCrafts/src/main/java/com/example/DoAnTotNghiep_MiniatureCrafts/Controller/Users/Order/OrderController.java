package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Users.Order;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.OrderLineDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.POSOrderDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.OrderService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.Voucher.VoucherService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vmgKtShop")
@CrossOrigin(value = "*")

public class OrderController {
    @Autowired
    private VariationService variationService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    OrderService orderService;

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
    @GetMapping("history/{id}/{order}")
    public List<POSOrderDTO> getOrderHistory(@PathVariable("id") Long customerid) {
        // Lấy danh sách các POSOrderDTO từ service
        List<POSOrderDTO> orders = orderService.getAllOrders(customerid);

        // Lặp qua từng đơn hàng và gắn danh sách OrderLineDTO vào đối tượng POSOrderDTO
        orders.forEach(order -> {
            // Lấy danh sách OrderLineDTO cho từng đơn hàng
            List<OrderLineDTO> orderLines = orderService.getOrdersLine(order.getID());
            // Gắn danh sách OrderLineDTO vào POSOrderDTO
            order.setOrderLine(orderLines);
        });

        return orders;
    }
    // khi bấm đặt hàng thì tạo hóa đơn r trực tiếp thêm sản phẩm đó vào hóa đơn
    // bấm đặt hàng\


    // trang đăng ký:
    // nhập thông tin
    // sau đó save vào csdl
    // tiếp tục đẩy đến trang tạo tài khoản
    // sau đó nhảy về trang order

    @PostMapping("/newOrder")
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
