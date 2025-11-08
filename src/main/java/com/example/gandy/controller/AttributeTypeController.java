package com.example.gandy.controller;

import com.example.gandy.entity.AttributeType;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.AttributeTypeRepository;
import com.example.gandy.service.AttributeTypeServiceImpl;
import com.example.gandy.service.WarrantyServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/attributeType")
public class AttributeTypeController {

    @Autowired
    AttributeTypeRepository repository;

    @Autowired
    AttributeTypeServiceImpl attributeTypeService;



    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody AttributeType attributeType) {
        attributeTypeService.createObjects(attributeType);
        return ResponseEntity.ok("attributeType register successfully!");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody AttributeType attributeType) {

        repository.findById(attributeType.getId())
                .map(item -> {
                    item.setAttributeType(attributeType.getAttributeType());
                    item.setProductType(attributeType.getProductType());
                    item.setSort(attributeType.getSort());
                    attributeTypeService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    attributeTypeService.createObjects(attributeType);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getAttributeTypeByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAttributeTypeByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(attributeTypeService.getAttributeTypeByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( attributeTypeService.getAllAttributeType(pageNumber));
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getByid(@PathVariable("id") long id) throws IOException {
        return ResponseEntity.ok( attributeTypeService.findObject(id));
    }



    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) throws IOException {
        attributeTypeService.deleteByIdObject(id);
        return ResponseEntity.ok("attributeType removed successfully!");
    }
}
