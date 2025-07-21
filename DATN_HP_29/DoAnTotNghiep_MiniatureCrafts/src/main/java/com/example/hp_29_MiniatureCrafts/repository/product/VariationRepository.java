package com.example.hp_29_MiniatureCrafts.repository.product;

import com.example.hp_29_MiniatureCrafts.entity.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Long> {
    @Query("select v from Variation v order by v.ID DESC")
    Page<Variation> findAll(Pageable pageable);

    @Query("SELECT v FROM Variation v ORDER BY v.Sold DESC")
    Page<Variation> top6BestSeller(Pageable pageable);

    @Query("SELECT v FROM Variation v WHERE v.Status = true ORDER BY v.ID DESC")
    Page<Variation> getVariationByStatus(Pageable pageable);

    @Query("SELECT v FROM Variation v WHERE (:minPrice IS NULL OR v.Price >= :minPrice) AND (:maxPrice IS NULL OR v.Price <= :maxPrice)")
    Page<Variation> findByPriceRange(Pageable pageable, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);


    @Query("SELECT v FROM Variation v WHERE " +
            "v.Name LIKE %:keyword% " +
            "OR v.ProductID.CategoryID.Name LIKE %:keyword% " +
            "OR v.Description LIKE %:keyword% " +
            "OR v.Material LIKE %:keyword% ")
    Page<Variation> findByName(Pageable pageable, @Param("keyword") String keyword);


    @Query("select v from Variation v where v.ID =:id")
    Variation findByID(@Param("id") Long id);

    @Query("select v from Variation v ORDER BY v.ID DESC")
    Page<Variation> newVariation(Pageable pageable);

    @Query("select v from Variation v where v.ID= :idvariation")
    Variation findByIdVariation(@Param("idvariation") Long id);

//
    @Query("select v from Variation v where v.ProductID.ID= :id order by v.ID DESC")
    List<Variation> findByProductID(@Param("id") Long id);

    @Query("select v from Variation v where v.ProductID.CategoryID.ID= :id order by v.ID DESC")
    Page<Variation> findProductbyCatergory(Pageable pageable, @Param("id") Long id);


}
