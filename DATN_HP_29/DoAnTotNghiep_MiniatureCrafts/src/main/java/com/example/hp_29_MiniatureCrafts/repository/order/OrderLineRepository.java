package com.example.hp_29_MiniatureCrafts.repository.order;

import com.example.hp_29_MiniatureCrafts.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    @Query(value = "SELECT o from OrderLine o where o.OderID =:orderID")
    List<OrderLine> findAllOrderID(@Param("orderID") Long orderID);

}