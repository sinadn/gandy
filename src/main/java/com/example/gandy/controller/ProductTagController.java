package com.example.gandy.controller;

import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.Tag;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.ProductTagServiceImpl;
import com.example.gandy.service.TagServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/ptag")
public class ProductTagController {
    @Autowired
    ProductTagServiceImpl productTagService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody ProductTag object) {
        productTagService.addObject(object);
        return ResponseEntity.ok("ProductTag has been registered.");
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(productTagService.getById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        productTagService.deleteById(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(productTagService.getAll(pageNumber));
    }


    @PostMapping("/getProductTagByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductTagByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(productTagService.findProductTagByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }



}
