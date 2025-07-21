package com.example.hp_29_MiniatureCrafts.controller.admin.auth;
import com.example.hp_29_MiniatureCrafts.dto.POSOrderDTO;
import com.example.hp_29_MiniatureCrafts.service.account.EmailService;
import com.example.hp_29_MiniatureCrafts.service.order.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("MiniatureCrafts")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    OrderService orderService;

    @GetMapping("/send-email/{to}/{orderID}")
    public String sendEmail(@PathVariable("to") String to,@PathVariable("orderID") Long orderID) throws MessagingException {
        POSOrderDTO order = orderService.findOrderById(orderID);
        System.out.println("tổng tiền: "+order.getTotal_Payment());
        System.out.println("địa chỉ: "+order.getAddress());
        emailService.sendOrderEmailByUser(
                to,
                order.getCustomerID().getName(),
                order.getOrderLine(),
                order.getTotal_Payment(),
                order.getAddress(),
                order.getPaymentMethod().getNote());
        emailService.sendOrderEmailByAdmin(
                order.getCustomerID().getName(),
                order.getOrderLine(),
                order.getTotal_Payment(),
                order.getAddress(),
                order.getPaymentMethod().getNote());
        return "Email sent successfully!";
    }
}

// note:
// khi user dat hang thanh cong thi admin se nhan email dat hang
//contact user gui contact luu vao db, sau do admin se nhan duoc email contact do
