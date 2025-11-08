package com.example.gandy.repo;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Users;
import com.example.gandy.entity.Warranty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegWarrantyRepository extends JpaRepository<RegWarranty, Integer> {

    @Query("SELECT w FROM RegWarranty w WHERE w.id=?1")
    RegWarranty findRegWarrantyById(int id);


    @Query("SELECT w FROM RegWarranty w   WHERE w.companyName like %:name%  ")
    List<RegWarranty> findRegWarrantyByWords(@Param("name") String name , Pageable pageable);


}