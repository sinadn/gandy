package com.example.gandy.repo;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.AttributeType;
import com.example.gandy.entity.ProductCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeOptionRepository extends JpaRepository<AttributeOption, Long> {

    @Query("SELECT a FROM AttributeOption a  WHERE a.attributeOption like %:name% and a.attributeType.id=:id  ")
    List<AttributeOption> findAttributeOptionByName(@Param("name") String name , @Param("id") long id , Pageable pageable);


    @Query("SELECT a FROM AttributeOption a  WHERE a.attributeType.productType.id IN :numbers")
    List<AttributeOption> findAttributeOptions(@Param("numbers") List<Long> numbers);


    @Query("SELECT a FROM AttributeOption a  WHERE a.id IN :numbers")
    List<AttributeOption> attributeOptionList(@Param("numbers") List<Integer> numbers);


    @Query("SELECT a FROM AttributeOption a  WHERE a.attributeType.id=:id  ")
    List<AttributeOption> getAttributeOptionByAT(@Param("id") long id);




    @Query("SELECT a FROM AttributeOption a  WHERE a.attributeOption like %:name%  ")
    List<AttributeOption> searchAttributeOption(@Param("name") String name , Pageable pageable);


}