package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query
//    Product findByName(String name);

}
