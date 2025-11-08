package com.example.gandy.service;

import com.example.gandy.entity.ProductSeen;
import com.example.gandy.entity.TagAmountBox;
import com.example.gandy.repo.ProductSeenRepository;
import com.example.gandy.repo.TagAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSeenServiceImpl {
    @Autowired
    ProductSeenRepository repository;


    public void createObjects(ProductSeen object) {
        repository.save(object);
    }


    public void deleteObjects(long id) {
        repository.deleteById(id);
    }


    public ProductSeen getById(Long id) {
        return repository.getProductSeenById(id);
    }


    public List<ProductSeen> getAllByUserId(long id , int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.getProductSeenByUsersId(id , paging);
    }


    public List<ProductSeen> getAllByProductCountId(long id , int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.getProductSeenByProductCountId(id , paging);
    }


    public Integer countProductSeenByProductCountId(long id) {
        return repository.countProductSeenByProductCountId(id);
    }


    public ProductSeen getProductSeenByUsersIdAndProductCountId(long userId , long productCountId) {
        return repository.getProductSeenByUsersIdAndProductCountId(userId , productCountId);
    }



}
