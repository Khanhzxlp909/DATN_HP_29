package com.example.hp_29_MiniatureCrafts.repository.product;

import com.example.hp_29_MiniatureCrafts.entity.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    Page<Variation> findAll(Pageable pageable);

    @Query("SELECT v FROM Variation v WHERE (:minPrice IS NULL OR v.Price >= :minPrice) AND (:maxPrice IS NULL OR v.Price <= :maxPrice)")
    Page<Variation> findByPriceRange(Pageable pageable, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);


    @Query("SELECT v FROM Variation v WHERE " +
            "v.ProductID.Name LIKE %:keyword% " +
            "OR v.ProductID.CategoryID.Name LIKE %:keyword% " +
            "OR v.Note LIKE %:keyword% " +
            "OR v.Material LIKE %:keyword% " +
            "OR v.BrandID.Name LIKE %:keyword%")
    Page<Variation> findByName(Pageable pageable, @Param("keyword") String keyword);


    @Query("select v from Variation v where v.ProductID.ID =:id")
    Variation findByID(@Param("id") Long id);

    @Query("select v from Variation v where v.ID= :idvariation")
    Variation findByIdVariation(@Param("idvariation") Long id);


    @Query("select v from Variation v where v.BrandID.ID= :id")
    Page<Variation> findByBrand(Pageable pageable, @Param("id") Long id);

    @Query("select v from Variation v where v.ProductID.CategoryID.ID= :id")
    Page<Variation> findProductbyCatrgory(Pageable pageable, @Param("id") Long id);

}
