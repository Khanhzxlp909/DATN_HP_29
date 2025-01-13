package com.example.hp_29_MiniatureCrafts.repository.product.warehouse;

import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface suppillerRepository extends JpaRepository<Supplier, Long> {
}
