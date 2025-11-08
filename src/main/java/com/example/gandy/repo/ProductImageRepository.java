package com.example.gandy.repo;

import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(long id);


   void deleteProductImageByImg(String imageName);

    ProductImage findProductImageById(long id);

    @Query("SELECT p FROM ProductImage p   WHERE p.product.name like %:name%  ")
    List<ProductImage> getProductImageByWords(@Param("name") String name , Pageable pageable);




//    @Modifying
//    @Query("delete from Factor f WHERE f.users.mobile=?1 and f.status=0")
//    void deleteCartAndFactor(String mobile);
//
//    void deleteFactorByUsersMobileAndStatus(String mobile , int status);


}