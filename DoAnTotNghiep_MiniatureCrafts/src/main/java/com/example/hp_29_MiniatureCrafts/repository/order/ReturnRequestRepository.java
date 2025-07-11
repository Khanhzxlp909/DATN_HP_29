package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    @Query("SELECT r FROM ReturnRequest r WHERE r.CustomerID.ID = :customerId")
    List<ReturnRequest> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT r FROM ReturnRequest r WHERE r.Status = : status")
    List<ReturnRequest> findByStatus(@Param("status") int status);

    @Query("SELECT r FROM ReturnRequest r WHERE r.RequestDate BETWEEN :start AND :end")
    List<ReturnRequest> findByRequestDateBetween(LocalDateTime start, LocalDateTime end);

}

