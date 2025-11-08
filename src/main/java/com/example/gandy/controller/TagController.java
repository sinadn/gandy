package com.example.gandy.controller;

import com.example.gandy.entity.Activation;
import com.example.gandy.entity.Tag;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.TagRepository;
import com.example.gandy.service.ActivationServiceImpl;
import com.example.gandy.service.TagServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagRepository repository;

    @Autowired
    TagServiceImpl tagService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Tag object) {
        tagService.addObject(object);
        return ResponseEntity.ok("tag has been registered.");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Tag tag) {

        repository.findById(tag.getId())
                .map(item -> {
                    item.setTag(tag.getTag());
                    tagService.addObject(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    tagService.addObject(tag);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        tagService.deleteById(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(tagService.getAll(pageNumber));
    }


    @PostMapping("/getTagByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getTagByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(tagService.findTagByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }



}
