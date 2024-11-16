package com.example.DoAnTotNghiep_MiniatureCrafts.Controller.Users.Order;


import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.OrderLineDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.DTO.POSOrderDTO;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
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

            System.out.println("ID ORDER: " + order.getID());

            // Nếu order được tạo thành công, tạo dòng đơn hàng
            if (order.getID() != null) {
                // Giả sử bạn nhận được thông tin dòng đơn hàng từ client (có thể được truyền dưới dạng JSON)
                // Tạo OrderLineDTO và gọi createOrderLine
                OrderLineDTO orderLineDTO = new OrderLineDTO();
                // Cập nhật thông tin cho orderLineDTO (tùy theo yêu cầu của bạn)
                // orderLineDTO.set...

                createOrderLine(orderLineDTO, order.getID());  // Gọi createOrderLine với thông tin dòng đơn hàng và orderID
            }

            POSOrderDTO responseDTO = orderService.mapOrderEntityToDTO(order);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (RuntimeException ex) {
            // Trả về thông báo lỗi với HTTP status 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> createOrderLine(@Valid @RequestBody OrderLineDTO orderLineDTO, Long orderID) {
        try {
            // Tạo dòng đơn hàng mới
            OrderLine orderline = orderService.addOrderline(orderLineDTO, orderID);

            // Chuyển đổi entity sang DTO để trả về response
            OrderLineDTO responseDTO = orderService.mapOrderLineEntityToDTO(orderline);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (RuntimeException ex) {
            // Trả về thông báo lỗi với HTTP status 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


}
