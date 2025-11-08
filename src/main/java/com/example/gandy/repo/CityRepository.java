package com.example.gandy.repo;

import com.example.gandy.entity.City;
import com.example.gandy.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City c WHERE c.province.id=?1")
    List<City> findCities(long i);

}