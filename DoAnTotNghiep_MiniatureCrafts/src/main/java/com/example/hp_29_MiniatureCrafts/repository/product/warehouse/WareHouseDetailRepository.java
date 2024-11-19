package com.example.hp_29_MiniatureCrafts.repository.product.warehouse;


import com.example.hp_29_MiniatureCrafts.entity.WareHouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareHouseDetailRepository extends JpaRepository<WareHouseDetails, Integer> {
    @Query("select h from WareHouseDetails h where h.Import= :id")
    List<WareHouseDetails> findByWarehouseId(@Param("id") Long id);

    @Query("select h from WareHouseDetails h where h.ID= :id")
    WareHouseDetails findByIDWHDT(@Param("id") Integer id);
}
