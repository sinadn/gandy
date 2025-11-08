package com.example.gandy.repo;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Article;
import com.example.gandy.entity.Cover;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CoverRepository extends JpaRepository<Cover, Integer> {


    @Query("SELECT c FROM Cover c WHERE c.id=?1")
    Cover findByCoverId(int id);

    @Query("SELECT c FROM Cover c WHERE c.position=?1")
    List<Cover> findCoversByPosition(int position);

    Cover findCoversByImage(String image);


//    void deleteCoverByImage(String imageName);

}