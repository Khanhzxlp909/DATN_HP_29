package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.POSOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<POSOrder, Integer> {
    @Query("select o from POSOrder o where o.ID = :id")
    POSOrder findByOrderID(@Param("id") Long id);

    Page<POSOrder> findAll(Pageable pageable);
}
