package com.example.hp_29_MiniatureCrafts.repository.product;

import com.example.hp_29_MiniatureCrafts.entity.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    Page<Variation> findAll(Pageable pageable);

    @Query("select v from Variation v where v.ProductID.Name LIKE %:name%")
    Page<Variation> findByName(Pageable pageable, @Param("name") String name);

    @Query("select v from Variation v where v.ID= :idvariation")
    Variation findByIdVariation(@Param("idvariation") Long id);


    @Query("select v from Variation v where v.BrandID.ID= :id")
    Page<Variation> findByBrand(Pageable pageable,@Param("id") Long id);

    @Query("select v from Variation v where v.ProductID.CategoryID.ID= :id")
    Page<Variation> findProductbyCatrgory(Pageable pageable,@Param("id") Long id);

}
