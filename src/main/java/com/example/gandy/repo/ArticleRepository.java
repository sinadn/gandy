package com.example.gandy.repo;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.City;
import com.example.gandy.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a WHERE a.id=?1")
    Article findByArticleId(int id);


    @Query("SELECT a FROM Article a WHERE a.title like %:name%  ")
    List<Article> getArticleByWords(@Param("name") String name , Pageable pageable);


    Article findArticleByTitle(String name);

    Article findArticleByUrl(String name);




//
//    @Query("SELECT a FROM Article a")
//    List<Article> findMainArticle(Pageable pageable);



}