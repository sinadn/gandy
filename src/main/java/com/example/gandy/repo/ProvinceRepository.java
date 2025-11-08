package com.example.gandy.repo;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Category;
import com.example.gandy.entity.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

//    @Query("SELECT a FROM Address a WHERE a.address=?1 and a.city=?2")
//    Address findCities(int i);

    @Query("SELECT p FROM Province p   WHERE p.name like %:name%  ")
    List<Province> findProvinceByWords(@Param("name") String name , Pageable pageable);


}