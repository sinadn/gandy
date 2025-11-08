package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.MainWare;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.MainWareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainWareServiceImpl {
    @Autowired
    MainWareRepository wareRepository;


    public void createObjects(MainWare objects) {
        wareRepository.save(objects);
    }

    public void deleteObjects(long id) {
        wareRepository.deleteById(id);
    }

    public List<MainWare> findObjects() {
        return wareRepository.findAll();
    }


    public MainWare getById(Long id) {
        return wareRepository.findMainWareById(id);
    }

    public Page<MainWare> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return wareRepository.findAll(paging);
    }

}
