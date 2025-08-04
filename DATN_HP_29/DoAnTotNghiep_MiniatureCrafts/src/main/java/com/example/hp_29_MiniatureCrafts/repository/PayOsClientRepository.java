package com.example.hp_29_MiniatureCrafts.repository;

import com.example.hp_29_MiniatureCrafts.entity.PayOsClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayOsClientRepository extends JpaRepository<PayOsClient, Long> {

    /**
     * Tự động tạo câu lệnh SELECT...WHERE OrderCode = ?
     * Rất quan trọng để tìm lại giao dịch khi nhận webhook từ PayOS.
     * Sửa kiểu dữ liệu của orderCode thành Long để đồng bộ.
     */
    Optional<PayOsClient> findByOrderCode(Long orderCode); // Sửa thành Long

}