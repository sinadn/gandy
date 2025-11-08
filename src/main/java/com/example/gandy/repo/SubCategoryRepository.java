package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("SELECT s FROM SubCategory s WHERE s.parentId=?1")
    List<SubCategory> findSubCategory(long parent);

    @Query("SELECT c FROM SubCategory c   WHERE c.subId.name like %:name%  ")
    List<SubCategory> findSubCategoryList(@Param("name") String name , Pageable pageable);

    @Query("SELECT c FROM SubCategory c WHERE c.parentId.isMain=?1 and c.parentId.isActive=?2")
    List<SubCategory> findCategory(long main , int active);

    @Query("SELECT c FROM SubCategory c WHERE c.parentId.isActive=1 and c.subId.isActive=1 ")
    List<SubCategory> getAllMenu(int active ,int active2);

    SubCategory getById(int id);

}