package com.example.gandy.repo;

import com.example.gandy.entity.CategoryBox;
import com.example.gandy.entity.Cover;
import com.example.gandy.entity.MainWare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryBoxRepository extends JpaRepository<CategoryBox, Integer> {
       CategoryBox findCategoryBoxById(Integer id);

       CategoryBox findCategoryBoxByName(String image);

}