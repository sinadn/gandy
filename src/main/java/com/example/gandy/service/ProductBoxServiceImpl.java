package com.example.gandy.service;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.ProductBox;
import com.example.gandy.repo.DiscountRepository;
import com.example.gandy.repo.ProductBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductBoxServiceImpl {
    @Autowired
    private ProductBoxRepository repository;


    public ProductBox findObject(int id) {
        return repository.findProductBoxById(id);
    }


    public void addObject(ProductBox object) {
        repository.save(object);
    }


    public Page<ProductBox> getAllObjects() {
        Pageable paging = PageRequest.of(0, 16, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }


    public void deleteByIdObject(Integer id) {
        repository.deleteById(id);
    }


    public Integer countProductBoxesByBoxNum(int id) {
       return repository.countProductBoxesByBoxNum(id);
    }


}
