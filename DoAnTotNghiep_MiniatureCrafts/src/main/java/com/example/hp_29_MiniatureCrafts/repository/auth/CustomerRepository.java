package com.example.hp_29_MiniatureCrafts.repository.auth;

import com.example.hp_29_MiniatureCrafts.entity.Customer;
import com.example.hp_29_MiniatureCrafts.entity.Variation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select c from Customer c where c.ID = :userID")
    Customer findByUsers(@Param("userID") Long userId);

    @Query("SELECT COUNT(a) > 0 FROM Customer a WHERE a.Phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

    @Query(value = "select c from Customer c where c.Phone = :phone")
    Customer findByPhone(@Param("phone") String phone);

    @Query("SELECT v FROM Customer v WHERE " +
            "v.Name LIKE %:keyword% " +
            "OR v.Address LIKE %:keyword% " +
            "OR v.Note LIKE %:keyword% " +
            "OR v.Phone LIKE %:keyword% ")
    List<Customer> findByName(@Param("keyword") String keyword);

}
