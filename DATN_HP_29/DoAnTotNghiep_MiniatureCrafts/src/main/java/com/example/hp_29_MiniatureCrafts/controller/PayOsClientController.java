package com.example.hp_29_MiniatureCrafts.controller;

import com.example.hp_29_MiniatureCrafts.dto.CreatePaymentRequest;
import com.example.hp_29_MiniatureCrafts.model.PayOsClient;
import com.example.hp_29_MiniatureCrafts.service.PayOsClientService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.PaymentData;
import vn.payos.type.CheckoutResponseData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class PayOsClientController {

    private final PayOsClientService payOsClientService;
    private final PayOS payos;

    @Autowired
    public PayOsClientController(PayOsClientService payOsClientService, PayOS payos) {
        this.payOsClientService = payOsClientService;
        this.payos = payos;
    }

    /**
     * TẠO LINK THANH TOÁN MỚI
     */
    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, String>> createPaymentLink(@RequestBody CreatePaymentRequest request) {
        try {
            // 1. Tạo mã đơn hàng duy nhất, dùng long để tránh tràn số
            long orderCode = System.currentTimeMillis();

            // 2. SỬA LỖI: Dùng Builder Pattern để tạo PaymentData
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(request.getAmount())
                    .description(request.getDescription())
                    .cancelUrl("http://localhost:5173/payment-cancel")
                    .returnUrl("http://localhost:5173/payment-success")
                    .build();

            // 3. Gọi API của PayOS để tạo link
            CheckoutResponseData data = payos.createPaymentLink(paymentData);
            String checkoutUrl = data.getCheckoutUrl(); // Dùng getter thay vì .get()

            // 4. Lưu lại bản ghi giao dịch vào DB
            PayOsClient newTransaction = new PayOsClient();
            newTransaction.setOderId(request.getOrderId());
            newTransaction.setOrderCode((int) orderCode); // Ép kiểu về int nếu cột trong DB là int
            newTransaction.setAmount(request.getAmount());
            newTransaction.setDescription(request.getDescription());
            newTransaction.setCheckoutUrl(checkoutUrl);
            payOsClientService.createTransaction(newTransaction);

            // 5. Trả về checkoutUrl cho frontend
            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", checkoutUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- CÁC HÀM QUẢN LÝ GIAO DỊCH HIỆN CÓ ---

    @GetMapping
    public ResponseEntity<List<PayOsClient>> getAllTransactions() {
        List<PayOsClient> transactions = payOsClientService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayOsClient> getTransactionById(@PathVariable Long id) {
        return payOsClientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order-code/{orderCode}")
    public ResponseEntity<PayOsClient> getTransactionByOrderCode(@PathVariable Integer orderCode) {
        return payOsClientService.findByOrderCode(orderCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PayOsClient> createTransaction(@RequestBody PayOsClient transaction) {
        try {
            PayOsClient createdTransaction = payOsClientService.createTransaction(transaction);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayOsClient> updateTransaction(@PathVariable Long id, @RequestBody PayOsClient transactionDetails) {
        try {
            PayOsClient updatedTransaction = payOsClientService.updateTransaction(id, transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable Long id) {
        try {
            payOsClientService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}