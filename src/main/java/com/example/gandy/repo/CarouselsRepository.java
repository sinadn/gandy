package com.example.gandy.repo;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.Carousels;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselsRepository extends JpaRepository<Carousels, Integer> {

    Carousels findCarouselsById(int id);

//    @Query("SELECT c FROM Carousels c WHERE c.option1.name like %:name% or c.option2.name like %:name% or c.option3.name like %:name% ")
//    List<Carousels> getCarouselsByName(@Param("name") String name);
}