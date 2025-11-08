package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.ProductType;
import com.example.gandy.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    @Query("SELECT p FROM ProductType p WHERE p.id=?1")
    ProductType findProductTypeBy(long id);

    @Query("SELECT p FROM ProductType p WHERE p.parentProductType.id=?1")
    List<ProductType> findParentPtype(long id);

    @Query("SELECT p FROM ProductType p WHERE p.name like %:name%  ")
    List<ProductType> getProductTypeByWords(@Param("name") String name , Pageable pageable);

    @Query("SELECT p FROM ProductType p WHERE p.name=:name ")
    ProductType getProductTypeByName(@Param("name") String name);



}