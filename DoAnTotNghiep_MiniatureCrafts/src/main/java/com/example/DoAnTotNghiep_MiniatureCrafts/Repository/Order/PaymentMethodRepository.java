package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
