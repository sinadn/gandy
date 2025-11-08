package com.example.gandy.controller;

import com.example.gandy.entity.ProductConfig;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.entity.Warranty;
import com.example.gandy.exception.DeleteImageException;
import com.example.gandy.payload.request.ProductConfigRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.ProductConfigRepository;
import com.example.gandy.service.ProductConfigServiceImpl;
import com.example.gandy.service.ProductServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/pconfig")
public class ProductConfigController {

    @Autowired
    ProductConfigRepository repository;

    @Autowired
    ProductConfigServiceImpl productConfigService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addConfig(@RequestBody ProductConfig productConfig) {
        productConfigService.createObjects(productConfig);
        return ResponseEntity.ok("ProductConfig register successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody ProductConfig productConfig) {
        repository.findById(productConfig.getId())
                .map(item -> {
                    item.setProduct(productConfig.getProduct());
                    item.setAttributeOption(productConfig.getAttributeOption());
                    productConfigService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    productConfigService.createObjects(productConfig);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getByid/{id}")
    public ResponseEntity<?> getByBrand(@PathVariable("id") long id) {
        return ResponseEntity.ok(productConfigService.findProductConfigById(id));
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( productConfigService.getAllProductConfig(pageNumber));
    }


    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        productConfigService.deleteByIdObject(id);
            return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @PostMapping("/searchPConfigByPName")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> searchPConfigByPName(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(productConfigService.searchPConfigByPName(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/searchPConfigByAO")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> searchPConfigByAO(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(productConfigService.searchPConfigByAO(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/getByProductid/{id}")
    public ResponseEntity<?> getByProductid(@PathVariable("id") long id) {
        return ResponseEntity.ok(productConfigService.findProductConfigByProduct(id));
    }


}
