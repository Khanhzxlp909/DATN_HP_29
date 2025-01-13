package com.example.hp_29_MiniatureCrafts.repository.thuoctinh;

import com.example.hp_29_MiniatureCrafts.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select b from Brand b order by b.ID desc")
    Page<Brand> findALL(Pageable pageable);

}
