package com.example.gandy.repo;

import com.example.gandy.entity.FooterMenu;
import com.example.gandy.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooterMenuRepository extends JpaRepository<FooterMenu, Long> {

    FooterMenu findFooterMenuById(long id);


    @Query("SELECT f FROM FooterMenu f WHERE f.name like %:name%  ")
    List<FooterMenu> getFooterMenuByWords(@Param("name") String name , Pageable pageable);


}