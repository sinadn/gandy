package com.example.gandy.repo;

import com.example.gandy.entity.Brand;
import com.example.gandy.entity.City;
import com.example.gandy.entity.ProductType;
import com.example.gandy.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findBrandById(int i);


    @Query("SELECT b FROM Brand b WHERE b.name like %:name%  ")
    List<Brand> getBrandByWords(@Param("name") String name , Pageable pageable);


}