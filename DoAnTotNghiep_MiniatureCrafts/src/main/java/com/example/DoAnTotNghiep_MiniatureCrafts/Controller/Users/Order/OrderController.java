package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Users.Order;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.OrderLineDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.POSOrderDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.OrderService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Order.Voucher.VoucherService;
import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Product.VariationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vmgKtShop")
public class OrderController {
    @Autowired
    private VariationService variationService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    OrderService orderService;

    @GetMapping("history/{id}")
    public List<POSOrderDTO> getAllOrder(@PathVariable("id") Long id){

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
    // lấy ra những sp có trong hóa đơn
    @GetMapping("history/{id}/{order}")
    public List<POSOrderDTO> getAllOrder(@PathVariable("id") Long id, @PathVariable("order") String orderID) {
        // Lấy danh sách các POSOrderDTO từ service
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
    // khi bấm đặt hàng thì tạo hóa đơn r trực tiếp thêm sản phẩm đó vào hóa đơn
    // bấm đặt hàng\
    // trang đăng ký:
    // nhập thông tin
    // sau đó save vào csdl
    // tiếp tục đẩy đến trang tạo tài khoản
    // sau đó nhảy về trang order

    @PostMapping("/newOrder/")
    public ResponseEntity<?> createOrder(@Valid @RequestBody POSOrderDTO posOrderDTO) {
        try {
            // Tạo order
            POSOrder order = orderService.addSHOP(posOrderDTO);

            // Chuyển đổi entity sang DTO để trả về response
            POSOrderDTO responseDTO = orderService.mapOrderEntityToDTO(order);

            // Trả về response với HTTP status 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (RuntimeException ex) {
            // Trả về thông báo lỗi với HTTP status 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Trả về lỗi server với HTTP status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tạo đơn hàng!");
        }
    }


}
