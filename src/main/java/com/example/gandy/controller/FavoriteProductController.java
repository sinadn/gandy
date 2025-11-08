package com.example.gandy.controller;

import com.example.gandy.entity.FavoriteProduct;
import com.example.gandy.entity.Tag;
import com.example.gandy.entity.Users;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.response.AmazingOfferResponse;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.FavoriteProductImpl;
import com.example.gandy.service.TagServiceImpl;
import com.example.gandy.utility.DiscountCalculation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/fp")
public class FavoriteProductController {
    @Autowired
    FavoriteProductImpl service;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody FavoriteProduct object) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        Users users = new Users();
        users.setId(userDetails1.getId());
        object.setUsers(users);
        service.createObjects(object);
        return ResponseEntity.ok("FavoriteProduct has been registered.");
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        FavoriteProduct favoriteProduct = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        try {
            favoriteProduct = service.findFavoriteProduct(id , userDetails1.getId());
        }catch (Exception e){
            e.getMessage();
        }
        if (favoriteProduct!=null)
        return ResponseEntity.ok(true);
        else {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        service.deleteObjects(id , userDetails1.getId());
        return ResponseEntity.ok("The desired item is deleted successfully!");
    }


    @PostMapping("/findAll")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation();
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculationForFavoriteProduct(service.findAll(userDetails1.getId())));
        return ResponseEntity.ok(amazingOfferResponse);
    }
}
