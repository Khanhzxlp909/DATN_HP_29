package com.example.hp_29_MiniatureCrafts.repository.product;

import com.example.hp_29_MiniatureCrafts.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer> {

    // dùng find by product để get ra list ảnh, sau đó map sang dto và gán list ảnh này vào images của product

    @Query("SELECT i FROM Images i WHERE i.Model = :model AND i.ProductID = :productID")
    List<Images> findByModelAndProductID(@Param("model") String model,
                                         @Param("productID") Long productID);

    @Query("SELECT i FROM Images i WHERE i.Model = :model AND i.ProductID = :productID")
    Images findImageByModelAndProductID(@Param("model") String model,
                                         @Param("productID") Long productID);

    @Modifying
    @Transactional
    @Query("DELETE FROM Images i WHERE i.Cd_Images = :cd_Images")
    void deleteByCd_Images(@Param("cd_Images") String cd_Images);


    @Query(value = "select i from Images i where i.Cd_Images= :cd_Images")
    Images findByCd_Images(@Param("cd_Images") String cd_Images);


}
