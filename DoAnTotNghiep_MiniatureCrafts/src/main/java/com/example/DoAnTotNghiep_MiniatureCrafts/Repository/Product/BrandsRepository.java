package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long> {
}
