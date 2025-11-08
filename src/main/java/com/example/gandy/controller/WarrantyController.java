package com.example.gandy.controller;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.Guarantee;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.WarrantyRepository;
import com.example.gandy.service.GuaranteeServiceImpl;
import com.example.gandy.service.WarrantyServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/warranty")
public class WarrantyController {

    @Autowired
    WarrantyRepository repository;

    @Autowired
    WarrantyServiceImpl warrantyService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody Warranty warranty) {
        warrantyService.createObjects(warranty);
        return ResponseEntity.ok("Warranty register successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Warranty warranty) {

        repository.findById(warranty.getId())
                .map(item -> {
                    item.setProduct(warranty.getProduct());
                    item.setRegWarranty(warranty.getRegWarranty());
                    warrantyService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    warrantyService.createObjects(warranty);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }



    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok( warrantyService.getAll());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( warrantyService.findAll(pageNumber));
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getByid(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( warrantyService.findWarranty(id));
    }


    @PostMapping("/getByProductId/{id}")
    public ResponseEntity<?> getByProductid(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( warrantyService.findWarrantyByProductId(id));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        warrantyService.deleteObjects(id);
        return ResponseEntity.ok("Warranty removed successfully!");
    }


    @PostMapping("/findWarrantyByproductName")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findWarrantyByproductName(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(warrantyService.findWarrantyByproductName(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}
