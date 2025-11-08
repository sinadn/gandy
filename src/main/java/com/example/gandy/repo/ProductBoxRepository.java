package com.example.gandy.repo;

import com.example.gandy.entity.MainWare;
import com.example.gandy.entity.ProductBox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoxRepository extends JpaRepository<ProductBox, Integer> {

       ProductBox findProductBoxById(int id);
       Integer countProductBoxesByBoxNum(int id);

}