package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
