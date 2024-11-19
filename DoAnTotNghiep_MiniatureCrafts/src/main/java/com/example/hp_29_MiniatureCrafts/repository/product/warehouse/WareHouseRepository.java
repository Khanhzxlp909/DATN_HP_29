package com.example.hp_29_MiniatureCrafts.repository.product.warehouse;


import com.example.hp_29_MiniatureCrafts.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {

    @Query("SELECT w FROM WareHouse w JOIN FETCH w.Employee WHERE w.ID = :id")
    WareHouse findByID(@Param("id") Long id);

}
