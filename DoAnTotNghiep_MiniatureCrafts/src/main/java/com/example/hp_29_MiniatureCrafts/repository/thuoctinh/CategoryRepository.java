package com.example.hp_29_MiniatureCrafts.repository.thuoctinh;

import com.example.hp_29_MiniatureCrafts.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 *
 * @author ASUS
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select c from Category c where c.Name LIKE  %:name%")
    List<Category> findByName(@Param("name") String name);

    @Query(value = "select c from Category c order by c.ID desc")
    Page<Category> findAll(Pageable pageable);
}
