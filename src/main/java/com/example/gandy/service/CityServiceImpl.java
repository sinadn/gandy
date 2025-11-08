package com.example.gandy.service;

import com.example.gandy.entity.City;
import com.example.gandy.entity.Province;
import com.example.gandy.repo.CityRepository;
import com.example.gandy.repo.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CityServiceImpl {
    @Autowired
    CityRepository cityRepository;

    public Collection<City> getObjects(long id) {
        return cityRepository.findCities(id);
    }


}
