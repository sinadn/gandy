package com.example.gandy.service;

import com.example.gandy.entity.Activation;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.Tag;
import com.example.gandy.repo.ActivationRepository;
import com.example.gandy.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl {
    @Autowired
    TagRepository repository;

    public void addObject(Tag object) {
        repository.save(object);
    }

    public void updateObject(Tag object) {
        repository.save(object);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Tag getById(Long id) {
        return repository.findTagById(id);
    }

    public Page<Tag> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }


    public List<Tag> findAll() {
        return repository.findAll();
    }

    public List<Tag> findTagByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  repository.findTagByWords(name , paging);
    }


}
