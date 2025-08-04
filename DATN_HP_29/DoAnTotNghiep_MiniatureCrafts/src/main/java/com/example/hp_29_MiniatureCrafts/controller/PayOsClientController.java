package com.example.hp_29_MiniatureCrafts.controller;

import com.example.hp_29_MiniatureCrafts.dto.CreatePaymentRequest;
import com.example.hp_29_MiniatureCrafts.entity.PayOsClient;
import com.example.hp_29_MiniatureCrafts.service.PayOsClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.CheckoutResponseData;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class PayOsClientController {

    private static final Logger logger = LoggerFactory.getLogger(PayOsClientController.class);
    private final PayOsClientService payOsClientService;

    @Autowired
    public PayOsClientController(PayOsClientService payOsClientService) {
        this.payOsClientService = payOsClientService;
    }

    /**
     * TẠO LINK THANH TOÁN MỚI (Tối ưu)
     * Controller giờ chỉ gọi service, logic đã được chuyển đi.
     */
    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, String>> createPaymentLink(@RequestBody CreatePaymentRequest request) {
        try {
            CheckoutResponseData data = payOsClientService.createPaymentLinkAndSaveTransaction(request);
            // Trả về checkoutUrl cho frontend
            Map<String, String> response = Collections.singletonMap("checkoutUrl", data.getCheckoutUrl());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to create payment link", e);
            Map<String, String> errorResponse = Collections.singletonMap("error", "Could not create payment link: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
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
    public ResponseEntity<PayOsClient> getTransactionByOrderCode(@PathVariable Long orderCode) { // Sửa thành Long
        return payOsClientService.findByOrderCode(orderCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Các endpoint còn lại giữ nguyên, không cần thay đổi nhiều
    // (Lưu ý: chúng cũng được hưởng lợi từ @Transactional trong Service)

    @PostMapping
    public ResponseEntity<PayOsClient> createTransaction(@RequestBody PayOsClient transaction) {
        PayOsClient createdTransaction = payOsClientService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
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
        payOsClientService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}