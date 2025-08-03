package com.example.hp_29_MiniatureCrafts.service;

import com.example.hp_29_MiniatureCrafts.dto.CreatePaymentRequest;
import com.example.hp_29_MiniatureCrafts.model.PayOsClient;
import com.example.hp_29_MiniatureCrafts.repository.PayOsClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PayOsClientService {

    private final PayOsClientRepository payOsClientRepository;
    private final PayOS payos; // Di chuyển PayOS dependency vào đây

    @Autowired
    public PayOsClientService(PayOsClientRepository payOsClientRepository, PayOS payos) {
        this.payOsClientRepository = payOsClientRepository;
        this.payos = payos;
    }

    /**
     * Tối ưu: Gộp logic tạo link và lưu DB vào một transaction duy nhất.
     * Controller chỉ cần gọi hàm này.
     */
    @Transactional
    public CheckoutResponseData createPaymentLinkAndSaveTransaction(CreatePaymentRequest request) throws Exception {
        long orderCode = System.currentTimeMillis();

        // Thêm các trường thông tin người mua
        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(request.getAmount())
                .description(request.getDescription())
                .cancelUrl("http://localhost:5173/payment-cancel")
                .returnUrl("http://localhost:5173/payment-success")
                // ✨ Bổ sung các thông tin này ✨
                .buyerName("Nguyễn Văn A") // Lấy từ thông tin đơn hàng
                .buyerEmail("nguyenvana@email.com") // Lấy từ thông tin đơn hàng
                .buyerPhone("0987654321") // Lấy từ thông tin đơn hàng
                .build();

        CheckoutResponseData checkoutResponseData = payos.createPaymentLink(paymentData);

        // Lưu giao dịch vào DB
        PayOsClient newTransaction = new PayOsClient();
        newTransaction.setOrderId(request.getOrderId());
        newTransaction.setOrderCode(orderCode); // Lưu kiểu Long
        newTransaction.setAmount(request.getAmount());
        newTransaction.setDescription(request.getDescription());
        newTransaction.setCheckoutUrl(checkoutResponseData.getCheckoutUrl());
        newTransaction.setStatus("PENDING");
        newTransaction.setCreationDate(LocalDateTime.now());
        payOsClientRepository.save(newTransaction);

        return checkoutResponseData;
    }

    @Transactional
    public PayOsClient createTransaction(PayOsClient transaction) {
        transaction.setCreationDate(LocalDateTime.now());
        if (transaction.getStatus() == null || transaction.getStatus().isEmpty()) {
            transaction.setStatus("PENDING");
        }
        return payOsClientRepository.save(transaction);
    }

    @Transactional(readOnly = true) // Chỉ đọc, không thay đổi dữ liệu
    public Optional<PayOsClient> findById(Long id) {
        return payOsClientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PayOsClient> findByOrderCode(Long orderCode) { // Sửa thành Long
        return payOsClientRepository.findByOrderCode(orderCode);
    }

    @Transactional(readOnly = true)
    public List<PayOsClient> getAllTransactions() {
        return payOsClientRepository.findAll();
    }

    @Transactional
    public PayOsClient updateTransaction(Long id, PayOsClient transactionDetails) {
        PayOsClient existingTransaction = payOsClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));

        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setDescription(transactionDetails.getDescription());
        existingTransaction.setStatus(transactionDetails.getStatus());

        return payOsClientRepository.save(existingTransaction);
    }

    @Transactional
    public PayOsClient updateTransactionStatus(Long orderCode, String newStatus) { // Sửa thành Long
        PayOsClient transaction = findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with order code: " + orderCode));

        transaction.setStatus(newStatus);
        if ("PAID".equalsIgnoreCase(newStatus)) {
            transaction.setPaymentDate(LocalDateTime.now());
        }
        return payOsClientRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        if (!payOsClientRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }
        payOsClientRepository.deleteById(id);
    }
}