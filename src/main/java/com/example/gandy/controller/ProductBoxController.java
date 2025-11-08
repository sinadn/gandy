package com.example.gandy.controller;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.ProductBox;
import com.example.gandy.payload.request.DiscountRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.DiscountServiceImpl;
import com.example.gandy.service.ProductBoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/pbox")
public class ProductBoxController {
    @Autowired
    ProductBoxServiceImpl service;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addObject(@RequestBody ProductBox productBox) {
        int size =service.countProductBoxesByBoxNum(productBox.getBoxNum());
        if (size<4 && (productBox.getBoxNum()>=1 && productBox.getBoxNum()<=4))
            service.addObject(productBox);
        return ResponseEntity.ok("ProductBox register successfully!");
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok( service.getAllObjects());
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductSuggestion(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.findObject(id));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        service.deleteByIdObject(id);
        return ResponseEntity.ok("productBox is removed successfully!");
    }
}
