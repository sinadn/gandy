package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Cover;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryRepository repository;


    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Category response = null;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, Category.class);
        if (file!=null) {
            response.setImgName(file.getOriginalFilename());
        }

        categoryService.createObjects(response);
        return ResponseEntity.ok("ProductImage has been registered successfully.");

    }



    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Category response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, Category.class);
        repository.findById(response.getId())
                .map(item -> {
                    item.setAmount(response.getAmount());
                    item.setProductType(response.getProductType());
                    item.setUrl(response.getUrl());
                    item.setAttributeOption(response.getAttributeOption());
                    item.setName(response.getName());
                    item.setProductTag(response.getProductTag());
                    item.setImgName(response.getImgName());
                    item.setOrderVal(response.getOrderVal());
                    item.setIsMain(response.getIsMain());
                    item.setIsActive(response.getIsActive());
                    if (file!=null) {
                        item.setImgName(file.getOriginalFilename());
                    }
                    categoryService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    if (file!=null) {
                        response.setImgName(file.getOriginalFilename());
                    }
                    categoryService.createObjects(response);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Cover registered successfully!");
    }





    @PostMapping("/main")
    public ResponseEntity<?> getMainMenu() {
        return ResponseEntity.ok(categoryService.findObjects());
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        categoryService.deleteObjects(id);
        return ResponseEntity.ok("category removed successfully!");
    }

    @PostMapping("/getCategoryByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(categoryService.findCategoryByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }


    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(categoryService.getAll(pageNumber));
    }


}
