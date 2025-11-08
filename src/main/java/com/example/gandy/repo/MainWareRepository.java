package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.MainWare;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainWareRepository extends JpaRepository<MainWare, Long> {

//    @Query("SELECT c FROM Category c   WHERE c.name like %:name%  ")
//    List<MainWare> findCategoryByWords(@Param("name") String name , Pageable pageable);
       MainWare findMainWareById(long id);
}