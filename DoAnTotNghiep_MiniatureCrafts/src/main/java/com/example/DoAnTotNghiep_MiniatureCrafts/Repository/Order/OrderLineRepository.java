package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Order;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    @Query(value = "SELECT o from OrderLine o where o.OderID =:OrderID")
    List<OrderLine> findAllOrderID(Long OrderID);
}
