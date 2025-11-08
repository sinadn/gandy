package com.example.gandy.service;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Category;
import com.example.gandy.entity.Province;
import com.example.gandy.repo.AddressRepository;
import com.example.gandy.repo.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProvinceServiceImpl{
    @Autowired
    ProvinceRepository provinceRepository;

    public List<Province> getObjects() {
        return provinceRepository.findAll();
    }


    public List<Province> findProvinceByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  provinceRepository.findProvinceByWords(name , paging);

    }

}
