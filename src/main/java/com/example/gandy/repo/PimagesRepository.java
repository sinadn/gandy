package com.example.gandy.repo;

import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PimagesRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT p FROM ProductImage p WHERE p.id=?1 ")
    ProductImage findProductById(long id);

}