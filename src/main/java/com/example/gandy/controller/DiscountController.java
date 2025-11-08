package com.example.gandy.controller;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.ProductSuggestion;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.DiscountRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.ProductSugRequest;
import com.example.gandy.repo.DiscountRepository;
import com.example.gandy.service.DiscountServiceImpl;
import com.example.gandy.service.ProductServiceImpl;
import com.example.gandy.service.productSuggestion.ProductSuggestionServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
    @Autowired
    DiscountServiceImpl service;

    @Autowired
    DiscountRepository repository;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addObject(@RequestBody DiscountRequest discountRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime create = LocalDateTime.parse(discountRequest.getCreate_at(), formatter);
        LocalDateTime expire = LocalDateTime.parse(discountRequest.getExpire_at(), formatter);
        Discount discount = new Discount();
        if (discountRequest.getId() != 0) {
            discount.setId(discountRequest.getId());
        }
        discount.setDiscount(discountRequest.getDiscount());
        discount.setCreate_at(create);
        discount.setExpire_at(expire);
        service.addObject(discount);
        return ResponseEntity.ok("discount register successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody DiscountRequest discountRequest) {
        Discount discount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime create = LocalDateTime.parse(discountRequest.getCreate_at(), formatter);
        LocalDateTime expire = LocalDateTime.parse(discountRequest.getExpire_at(), formatter);
        discount = service.findObject(discountRequest.getId());
        discount.setDiscount(discountRequest.getDiscount());
        discount.setCreate_at(create);
        discount.setExpire_at(expire);
        service.addObject(discount);
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(service.getAllObjects(pageNumber));
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductSuggestion(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findObject(id));
    }

    @PostMapping("/getDiscountByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductSuggestion(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(service.getDiscountByWords(productRequest.getName()));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) throws IOException {
        service.deleteByIdObject(id);
        return ResponseEntity.ok("discount is removed successfully!");
    }
}
