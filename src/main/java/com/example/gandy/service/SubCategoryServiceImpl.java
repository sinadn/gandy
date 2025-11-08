package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.SubCategory;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.SubCategoryRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryServiceImpl {
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public List<SubCategory> findObjects(long main) {
        return subCategoryRepository.findSubCategory(main);
    }

    public SubCategory getById(int id) {
        return subCategoryRepository.getById(id);
    }


    public List<SubCategory> findMain(long main, int active) {
        return subCategoryRepository.findCategory(main , active);
    }

    public List<SubCategory> getAllMenu() {
        return subCategoryRepository.getAllMenu(1 , 1);
    }


    public Page<SubCategory> findAllMenu(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return subCategoryRepository.findAll(paging);
    }

    public void createObjects(SubCategory subCategory) {
         subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(int id) {
        subCategoryRepository.deleteById((long) id);
    }


    public List<SubCategory> findSubCategoryByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  subCategoryRepository.findSubCategoryList(name , paging);

    }











}
