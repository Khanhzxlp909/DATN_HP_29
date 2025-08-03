package com.example.hp_29_MiniatureCrafts.service;

import com.example.hp_29_MiniatureCrafts.model.PayOsClient;
import com.example.hp_29_MiniatureCrafts.repository.PayOsClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PayOsClientService {

    private final PayOsClientRepository payOsClientRepository;

    @Autowired
    public PayOsClientService(PayOsClientRepository payOsClientRepository) {
        this.payOsClientRepository = payOsClientRepository;
    }

    /**
     * Lưu một giao dịch mới vào DB.
     * Thường được gọi sau khi tạo link thanh toán thành công.
     */
    public PayOsClient createTransaction(PayOsClient transaction) {
        transaction.setCreationDate(LocalDateTime.now());
        if (transaction.getStatus() == null) {
            transaction.setStatus("PENDING"); // Gán trạng thái ban đầu nếu chưa có
        }
        return payOsClientRepository.save(transaction);
    }

    /**
     * Tìm giao dịch bằng ID.
     */
    public Optional<PayOsClient> findById(Long id) {
        return payOsClientRepository.findById(id);
    }

    /**
     * Tìm giao dịch bằng orderCode.
     */
    public Optional<PayOsClient> findByOrderCode(Integer orderCode) {
        return payOsClientRepository.findByOrderCode(orderCode);
    }

    /**
     * Lấy tất cả các giao dịch.
     */
    public List<PayOsClient> getAllTransactions() {
        return payOsClientRepository.findAll();
    }

    /**
     * Cập nhật thông tin một giao dịch dựa trên ID.
     */
    public PayOsClient updateTransaction(Long id, PayOsClient transactionDetails) {
        // Tìm giao dịch hiện có, nếu không thấy sẽ báo lỗi
        PayOsClient existingTransaction = payOsClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));

        // Cập nhật các trường từ thông tin được cung cấp
        existingTransaction.setAmount(transactionDetails.getAmount());
        existingTransaction.setDescription(transactionDetails.getDescription());
        existingTransaction.setStatus(transactionDetails.getStatus());
        // Bạn có thể thêm các trường khác cần cập nhật ở đây

        return payOsClientRepository.save(existingTransaction);
    }

    /**
     * Cập nhật trạng thái giao dịch khi nhận webhook.
     */
    public PayOsClient updateTransactionStatus(Integer orderCode, String newStatus) {
        PayOsClient transaction = findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with order code: " + orderCode));

        transaction.setStatus(newStatus);
        if ("PAID".equalsIgnoreCase(newStatus)) {
            transaction.setPaymentDate(LocalDateTime.now());
        }
        return payOsClientRepository.save(transaction);
    }

    /**
     * Xóa một giao dịch dựa trên ID.
     */
    public void deleteTransaction(Long id) {
        // Kiểm tra xem giao dịch có tồn tại không trước khi xóa
        if (!payOsClientRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found with id: " + id);
        }
        payOsClientRepository.deleteById(id);
    }
}
