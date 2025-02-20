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

//    Page<POSOrder> findAll(Pageable pageable);

    @Query("SELECT p FROM POSOrder p where p.CustomerID.ID = :id and p.Status = 1 ORDER BY p.ID DESC")
    Page<POSOrder> findAllBYDesc(Pageable pageable, @Param("id") Long customer_id);

}
