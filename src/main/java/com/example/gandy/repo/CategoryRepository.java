package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.City;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.isMain=?1 and c.isActive=?2")
    List<Category> findCategory(long main , int active);

    @Query("SELECT c FROM Category c WHERE c.id=?1")
    Category getCategoryById(long id);


    @Query("SELECT c FROM Category c   WHERE c.name like %:name%  ")
    List<Category> findCategoryByWords(@Param("name") String name , Pageable pageable);


    Category findCategoryById(long id);



}