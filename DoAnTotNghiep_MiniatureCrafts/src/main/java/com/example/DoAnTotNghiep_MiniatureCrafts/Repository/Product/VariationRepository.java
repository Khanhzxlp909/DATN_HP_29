package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    Page<Variation> findAll(Pageable pageable);

    @Query("select v from Variation v where v.ProductID.Name LIKE %:name%")
    Page<Variation> findByName(Pageable pageable, @Param("name") String name);



}
