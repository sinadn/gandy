package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.City;
import com.example.gandy.entity.SubCategory;
import com.example.gandy.entity.Tag;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CategoryServiceImpl {
    @Autowired
    CategoryRepository categoryRepository;



    public void createObjects(Category objects) {
        categoryRepository.save(objects);
    }


    public void updateObjects(CategoryRequest categoryRequest) {
        Category category = new Category();
        category= categoryRepository.getCategoryById(categoryRequest.id);
        category.setIsMain(categoryRequest.getIsMain());
        category.setName(categoryRequest.getName());
        category.setUrl(categoryRequest.getUrl());
        category.setImgName(categoryRequest.getImgName());
        category.setProductType(categoryRequest.getProductType());
        categoryRepository.save(category);
    }


    public void deleteObjects(long id) {
        categoryRepository.deleteById(id);
    }


    public List<Category> findObjects() {
        return categoryRepository.findCategory(1, 1);
    }


    public Category getById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    public Page<Category> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return categoryRepository.findAll(paging);
    }


    public List<Category> findCategoryByWords(String name) {
        Pageable paging = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        return  categoryRepository.findCategoryByWords(name , paging);

    }



}
