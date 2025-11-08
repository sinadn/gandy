package com.example.gandy.controller;

import com.example.gandy.entity.FooterMenu;
import com.example.gandy.entity.FooterSubMenu;
import com.example.gandy.exception.ResourceNotFoundException;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.FooterSubMenuRepository;
import com.example.gandy.service.footerSubMenu.FooterSubMenuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/footerSubMenu")
public class FooterSubMenuController {

    @Autowired
    FooterSubMenuRepository repository;

    @Autowired
    FooterSubMenuServiceImpl service;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody FooterSubMenu object) {
        service.addObject(object);
        return ResponseEntity.ok("Information has been successfully registered.");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody FooterSubMenu object) {
        repository.findById(object.getId())
                .map(item -> {
                    item.setContent(object.getContent());
                    item.setName(object.getName());
                    item.setFooterMenu(object.getFooterMenu());
                    item.setAmount(object.getAmount());
                    item.setAttributeOption(object.getAttributeOption());
                    item.setProductTag(object.getProductTag());
                    item.setProductType(object.getProductType());
                    item.setUrl(object.getUrl());
                    service.addObject(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    service.addObject(object);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getById/{id}")
    public Optional<?> getById(@Valid @PathVariable long id) {
        return service.getByIdObject(id);
    }


    @PostMapping("/getFooterSubMenuByName")
    public ResponseEntity<?> getFooterSubMenuByName(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(service.getFooterSubMenuByName(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/findFooterSubMenuByUrl")
    public ResponseEntity<?> findFooterSubMenuByUrl(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(service.findFooterSubMenuByUrl(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllObjects());
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        service.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }




}
