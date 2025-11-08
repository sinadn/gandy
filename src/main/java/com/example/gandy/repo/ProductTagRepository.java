package com.example.gandy.repo;

import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    ProductTag findProductTagById(long id);

    @Query("SELECT p FROM ProductTag p WHERE p.product.name like %:name%  ")
    List<ProductTag> findProductTagByWords(@Param("name") String name , Pageable pageable);

}