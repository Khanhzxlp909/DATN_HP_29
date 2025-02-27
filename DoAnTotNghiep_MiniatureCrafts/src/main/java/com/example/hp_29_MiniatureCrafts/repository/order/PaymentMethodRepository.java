package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
