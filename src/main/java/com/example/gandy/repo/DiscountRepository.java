package com.example.gandy.repo;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.ProductSuggestion;
import com.example.gandy.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findDiscountById(long id);

    @Query("SELECT d FROM Discount d WHERE d.discount like %:name%  ")
    List<Discount> getDiscountByNumber(@Param("name") String name , Pageable pageable);

}