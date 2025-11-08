package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.CategoryBox;
import com.example.gandy.entity.Cover;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryBoxRepository;
import com.example.gandy.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryBoxServiceImpl {
    @Autowired
    CategoryBoxRepository categoryBoxRepository;

    public void add(CategoryBox object) {
        categoryBoxRepository.save(object);
    }

    public void delete(int id) {
        categoryBoxRepository.deleteById(id);
    }


    public void update(CategoryBox categoryBox , MultipartFile file) {
        categoryBoxRepository.findById(categoryBox.getId())
                .map(item -> {
                    item.setMain(categoryBox.getMain());
                    item.setNum(categoryBox.getNum());
                    item.setName(categoryBox.getName());
                    item.setProductType(categoryBox.getProductType());
                    if (file!=null) {
                        item.setImg(file.getOriginalFilename());
                    }
                    categoryBoxRepository.save(item);
                    return ResponseEntity.ok("categoryBox updated successfully!");
                }).orElseGet(() -> {
                    if (file!=null) {
                        categoryBox.setImg(file.getOriginalFilename());
                    }
                    categoryBoxRepository.save(categoryBox);
                    return ResponseEntity.ok("categoryBox register successfully!");
                });

    }


    public List<CategoryBox> findAll() {
        return categoryBoxRepository.findAll();
    }


    public CategoryBox getById(int id) {
        return categoryBoxRepository.findCategoryBoxById(id);
    }


    public void deleteCategoryBoxByImgName(String name) {
        CategoryBox categoryBox = categoryBoxRepository.findCategoryBoxByName(name);
        categoryBox.setImg("");
        categoryBoxRepository.save(categoryBox);
    }


}
