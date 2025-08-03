package com.example.hp_29_MiniatureCrafts.repository; // Thay đổi package cho phù hợp

import com.example.hp_29_MiniatureCrafts.model.PayOsClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayOsClientRepository extends JpaRepository<PayOsClient, Long> {

    /**
     * Tự động tạo câu lệnh SELECT...WHERE OrderCode = ?
     * Rất quan trọng để tìm lại giao dịch khi nhận webhook từ PayOS.
     * Dùng Optional để xử lý trường hợp không tìm thấy một cách an toàn.
     */
    Optional<PayOsClient> findByOrderCode(Integer orderCode);

}