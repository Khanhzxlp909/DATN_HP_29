package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select c from Customer c where c.ID = :userID")
    List<Customer> findByUsers(@Param("userID") Long userId);
}