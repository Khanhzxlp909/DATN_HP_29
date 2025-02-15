package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "select c from CartItem c where c.Status = 1 and c.customer_id.ID=: cus_id")
    Page<CartItem> findAllCart(Pageable pageable, @Param("cus_id") Long cus_id);

    @Query(value = "select c from CartItem c where c.variation_id = :var_id and c.customer_id.ID=: cus_id")
    List<CartItem> findAllCartByUserAndVariation(@Param("cus_id") Long cus_id, @Param("var_id") Long var_id);
}
