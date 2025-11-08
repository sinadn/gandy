package com.example.gandy.service;

import com.example.gandy.entity.Brand;
import com.example.gandy.entity.ProductType;
import com.example.gandy.entity.Province;
import com.example.gandy.entity.Users;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.repo.BrandRepository;
import com.example.gandy.repo.ProvinceRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BrandServiceImpl {
    @Autowired
    BrandRepository brandRepository;


    public void addObject(Brand object) {
         brandRepository.save(object);
    }

    public Brand findObjects(int id) {
        return brandRepository.findBrandById(id);
    }

    public List<Brand> getBrandByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  brandRepository.getBrandByWords(name , paging);

    }

    public Page<Brand> getObjects(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return brandRepository.findAll(paging);
    }

    public void deleteObjects(int id) {
        brandRepository.deleteById(id);
    }


}
