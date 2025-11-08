package com.example.gandy.repo;

import com.example.gandy.entity.AttributeType;
import com.example.gandy.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

    @Query("SELECT a FROM AttributeType a  WHERE a.attributeType like %:name%  ")
    List<AttributeType> findAttributeTypeByName(@Param("name") String name , Pageable pageable);

}