package com.example.hp_29_MiniatureCrafts.controller.admin.orderPOS;

import com.example.hp_29_MiniatureCrafts.dto.OrderLineDTO;
import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
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
/**
 * Controller xử lý các thao tác liên quan đến đơn hàng POS tại trang admin
 */
@RestController
@RequestMapping("/admin/orders")
public class POSOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * API để tạo đơn trả hàng
     *
     * @param dto thông tin đơn hàng trả về
     * @return POSOrder đã tạo
     */
    @PostMapping("/accpect/return/{id}")
    public ResponseEntity<POSOrder> returnsAccepted(@PathVariable("id") Long id,@RequestBody POSOrderDTO dto) {
        try {

            POSOrder savedOrder = orderService.accpectReturn(dto);
            orderService.completeReturnOrder(id);
            return ResponseEntity.ok(savedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Xác nhận duyệt trả hàng một đơn hàng theo ID
     *
     * @param orderId ID của đơn hàng cần xác nhận
     * @return thông báo xác nhận thành công
     */
    @GetMapping("/returns/{orderId}")
    @Transactional
    public String createReturn(@PathVariable Long orderId, @RequestParam("note") String note) {
        try {
            orderService.accpectReturn(orderId, note);
            return "Gửi đơn thành công";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Lấy danh sách sản phẩm (OrderLine) trong một đơn hàng cụ thể
     *
     * @param orderid ID của đơn hàng
     * @return danh sách sản phẩm thuộc đơn hàng
     */
    @GetMapping("history/getprd/{orderid}")
    public List<OrderLineDTO> getOrderHistory(@PathVariable("orderid") Long orderid) {
        return orderService.getOrdersLine(orderid);
    }

    /**
     * Hủy một đơn hàng theo ID
     *
     * @param orderId ID của đơn hàng cần hủy
     * @return thông báo kết quả hủy
     */
    @GetMapping("/cancelOrder/{orderId}")
    @Transactional
    public String cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return "huỷ thành công";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Xác nhận hoàn thành một đơn hàng theo ID
     *
     * @param orderId ID của đơn hàng cần xác nhận
     * @return thông báo xác nhận thành công
     */
    @GetMapping("/sucressorder/{orderId}")
    @Transactional
    public String sucressorder(@PathVariable Long orderId) {
        try {
            orderService.successOrder(orderId);
            return "huỷ thành công";
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }



    /**
     * Lấy lịch sử đơn hàng của một khách hàng cụ thể (kèm theo thông tin sản phẩm và trạng thái)
     *
     * @param pageable phân trang
     * @param id       ID của khách hàng
     * @return danh sách đơn hàng đã được gắn statusText
     */
    @GetMapping("history/{id}")
    public Page<POSOrderDTO> getAllOrderByCustomer(Pageable pageable, @PathVariable("id") Long id) {
        Page<POSOrderDTO> orders = orderService.getAllOrdersbyCustomer(pageable, id);

        orders.forEach(order -> {
            List<OrderLineDTO> orderLines = orderService.getOrdersLine(order.getID());
            order.setOrderLine(orderLines);
            order.setStatusText(mapStatusToText(order.getStatus()));
        });

        return orders;
    }

    /**
     * Chuyển đổi mã trạng thái đơn hàng thành chuỗi hiển thị
     *
     * @param status mã trạng thái
     * @return chuỗi mô tả tương ứng
     */
    private String mapStatusToText(int status) {
        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(0, "Đã hủy");
        statusMap.put(1, "Chờ xác nhận");
        statusMap.put(2, "Đang giao hàng");
        statusMap.put(3, "Đã giao hàng thành công");
        statusMap.put(4, "Trả hàng");
        statusMap.put(5, "Chờ duyệt trả hàng");

        return statusMap.getOrDefault(status, "Không xác định");
    }

    /**
     * Lấy danh sách toàn bộ đơn hàng (phân trang)
     *
     * @param pageable thông tin phân trang
     * @return danh sách POSOrderDTO
     */
    @GetMapping("findall")
    public Page<POSOrderDTO> getAllOrder(Pageable pageable) {
        return orderService.findAllOrder(pageable);
    }

    /**
     * Tạo mới một đơn hàng POS
     *
     * @param posOrderDTO thông tin đơn hàng cần tạo
     * @return DTO của đơn hàng đã tạo hoặc lỗi nếu có
     */
    @PostMapping("/newOrder")
    public ResponseEntity<?> createOrder(@RequestBody POSOrderDTO posOrderDTO) {
        try {
            POSOrder order = orderService.orderInPOS(posOrderDTO);
            POSOrderDTO responseDTO = orderService.mapOrderEntityToDTO(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
