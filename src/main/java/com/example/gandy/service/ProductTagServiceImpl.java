package com.example.gandy.service;

import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.Tag;
import com.example.gandy.repo.ProductTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTagServiceImpl {
    @Autowired
    ProductTagRepository repository;

    public void addObject(ProductTag object) {
        repository.save(object);
    }

    public void updateObject(ProductTag object) {
        repository.save(object);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ProductTag getById(Long id) {
        return repository.findProductTagById(id);
    }

    public Page<ProductTag> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }

    public List<ProductTag> findProductTagByWords(String name) {
        Pageable paging = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "id"));
        return  repository.findProductTagByWords(name , paging);
    }


}
