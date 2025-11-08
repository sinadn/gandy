package com.example.gandy.controller;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.Category;
import com.example.gandy.entity.ProductType;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.ProductTypeRequest;
import com.example.gandy.repo.ProductTypeRepository;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.ProductTypeServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/ptype")
public class ProductTypeController {

    @Autowired
    ProductTypeRepository repository;

    @Autowired
    ProductTypeServiceImpl productTypeService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addProductType(@RequestBody ProductType productType) {
        productTypeService.createObjects(productType);
        return ResponseEntity.ok("productType register successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody ProductType productType) {
        repository.findById(productType.getId())
                .map(item -> {
                    item.setName(productType.getName());
                    item.setParentProductType(productType.getParentProductType());
                    productTypeService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    productTypeService.createObjects(productType);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductType(@PathVariable("id") long id) {
        return ResponseEntity.ok(productTypeService.findObject(id));
    }


    @PostMapping("/getProductTypeByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductTypeByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(productTypeService.getProductTypeByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) throws IOException {
        productTypeService.deleteObjects(id);
        return ResponseEntity.ok("productType removed successfully!");
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getProductType() {
        return ResponseEntity.ok(productTypeService.getAll());
    }

    @PostMapping("/getAllType/{id}")
    public ResponseEntity<?> getAllType(@PathVariable("id") long id) {
        ProductType productType=null;
        productType = productTypeService.findObject(id);
        return ResponseEntity.ok(productType);
    }

    @PostMapping("/getAllProductType/{pageNumber}")
    public ResponseEntity<?> getAllProductType(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(productTypeService.getAllProductType(pageNumber));
    }


}
