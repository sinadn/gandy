package com.example.gandy.controller;

import com.example.gandy.entity.Brand;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.BrandRepository;
import com.example.gandy.service.BrandServiceImpl;
import com.example.gandy.service.CityServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    BrandRepository repository;

    @Autowired
    BrandServiceImpl brandService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Brand brand) {
        brandService.addObject(brand);
        return ResponseEntity.ok("add brand successfully");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Brand brand) {

        repository.findById(brand.getId())
                .map(item -> {
                    item.setName(brand.getName());
                    brandService.addObject(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    brandService.addObject(brand);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }

    @PostMapping("/getbyId/{brandId}")
    public ResponseEntity<?> getBrand(@PathVariable("brandId") int brandId) {
        return ResponseEntity.ok(brandService.findObjects(brandId));
    }


    @PostMapping("/getBrandByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getBrandByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(brandService.getBrandByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/all/{pageNumber}")
    public ResponseEntity<?> getAllBrand(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(brandService.getObjects(pageNumber));
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delMenu(@PathVariable("id") int id) {
        brandService.deleteObjects(id);
        return ResponseEntity.ok("brand removed successfully!");
    }

}
