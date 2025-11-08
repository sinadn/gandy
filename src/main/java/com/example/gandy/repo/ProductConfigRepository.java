package com.example.gandy.repo;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductConfigRepository extends JpaRepository<ProductConfig, Long> {

    @Query("SELECT p FROM ProductConfig p WHERE p.id=?1 ")
    ProductConfig findProductConfigById(long id);

    @Query("SELECT p FROM ProductConfig p WHERE p.product.id=?1 ")
    List<ProductConfig> findProductConfigByProduct(long id);


    @Query("SELECT p FROM ProductConfig p  WHERE p.product.name like %:name%  ")
    List<ProductConfig> searchPConfigByPName(@Param("name") String name , Pageable pageable);


    @Query("SELECT p FROM ProductConfig p  WHERE p.attributeOption.attributeOption like %:name%  ")
    List<ProductConfig> searchPConfigByAO(@Param("name") String name , Pageable pageable);


}