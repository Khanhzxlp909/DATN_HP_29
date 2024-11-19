package com.example.hp_29_MiniatureCrafts.repository.product;

import com.example.hp_29_MiniatureCrafts.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//
//    @Query(value = "select p from Product p where p.CategoryID.ID= :category")
//    Page<Product> findProductbyCatrgory(Pageable pageable, @Param("category") Long id);

}
