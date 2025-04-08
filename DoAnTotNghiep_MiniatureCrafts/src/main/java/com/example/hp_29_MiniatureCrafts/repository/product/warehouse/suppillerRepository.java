package com.example.hp_29_MiniatureCrafts.repository.product.warehouse;

import com.example.hp_29_MiniatureCrafts.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface suppillerRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE s.Status = true")
    List<Supplier> findAllByStatus();

    Supplier findByID(Integer id);
}
