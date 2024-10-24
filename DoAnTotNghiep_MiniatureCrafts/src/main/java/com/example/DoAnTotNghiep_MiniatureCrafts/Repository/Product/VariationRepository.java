package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    Page<Variation> findAll(Pageable pageable);


}
