package com.example.gandy.repo;

import com.example.gandy.entity.Activation;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
//    @Query("SELECT a FROM Activation a WHERE  a.users.mobile=?1 and a.otp=?2 order by a.id desc ")
    Tag findTagById(long id);



    @Query("SELECT t FROM Tag t WHERE t.tag like %:name%  ")
    List<Tag> findTagByWords(@Param("name") String name , Pageable pageable);

}