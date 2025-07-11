package com.example.hp_29_MiniatureCrafts.controller.admin.VNPay;

import com.example.hp_29_MiniatureCrafts.service.order.VNPayService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {

    private final VNPayService vnpayService;

    public VNPayController(VNPayService vnpayService) {
        this.vnpayService = vnpayService;
    }

    @GetMapping("/create_payment")
    public Map<String, String> createPayment(@RequestParam Long amount, @RequestParam String orderId, @RequestParam String ipAddr) {
        String paymentUrl = vnpayService.createPaymentUrl(amount, orderId, ipAddr);
        return Map.of("paymentUrl", paymentUrl);
    }

    @GetMapping("/callback")
    public String paymentCallback(@RequestParam Map<String, String> params) {

        return "Kết quả thanh toán: " + vnpayService.paymentCallback(params);
    }
}
