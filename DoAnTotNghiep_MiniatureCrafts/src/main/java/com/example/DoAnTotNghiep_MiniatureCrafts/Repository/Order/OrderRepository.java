package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.POSOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<POSOrder, Integer> {
}
