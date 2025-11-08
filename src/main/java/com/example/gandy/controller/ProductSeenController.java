package com.example.gandy.controller;

import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.ProductSeen;
import com.example.gandy.entity.Users;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.SearchProductRequest;
import com.example.gandy.payload.response.AmazingOfferResponse;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.repo.ProductSeenRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.service.ProductSeenServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.DiscountCalculation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/productSeen")
public class ProductSeenController {

    @Autowired
    ProductSeenRepository repository;

    @Autowired
    ProductSeenServiceImpl service;




    @PostMapping("/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addProductSeen(@PathVariable("id") long id) {
            ProductSeen check = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;

        try {
            check = service.getProductSeenByUsersIdAndProductCountId(userDetails1.getId() , id);
        }catch (Exception e){
            e.getMessage();
        }

        if (check==null){
            check = new ProductSeen();
            ProductCount productCount= new ProductCount();
            Users users = new Users();
            productCount.setId(id);
            users.setId(userDetails1.getId());
            check.setUsers(users);
            check.setProductCount(productCount);
            service.createObjects(check);
        }
        return ResponseEntity.ok("ProductSeen register successfully!");
    }



    @PostMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getProductCountList(@PathVariable("pageNumber") int pageNumber) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        return ResponseEntity.ok(service.getAllByUserId(userDetails1.getId(), pageNumber));
    }





}
