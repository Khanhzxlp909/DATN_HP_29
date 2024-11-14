package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Product;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Category;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select c from Category c where c.Name LIKE  %:name%")
    List<Category> findByName(@Param("name") String name);
}
