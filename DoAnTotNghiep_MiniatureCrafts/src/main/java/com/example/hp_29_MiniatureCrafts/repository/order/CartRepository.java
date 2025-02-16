package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.customer_id.ID= :customerId")
    List<CartItem> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT c FROM CartItem c WHERE c.customer_id.ID = :customerId AND c.variation_id.ID = :variationId and c.Status = 1")
    CartItem findByCustomerAndVariation(@Param("customerId") Long customerId, @Param("variationId") Long variationId);

}
