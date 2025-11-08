package com.example.gandy.repo;

import com.example.gandy.entity.Guarantee;
import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.Warranty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

    @Query("SELECT w FROM Warranty w WHERE w.id=?1")
    Warranty findWarrantyById(int id);

    @Query("SELECT w FROM Warranty w WHERE w.product.id=?1")
    List<Warranty> findWarrantyByProductId(int id);

    @Query("SELECT w FROM Warranty w WHERE w.product.name like %:name%  ")
    List<Warranty> findWarrantyByproductName(@Param("name") String name , Pageable pageable);


}