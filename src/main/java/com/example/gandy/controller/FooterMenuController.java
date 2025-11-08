package com.example.gandy.controller;

import com.example.gandy.entity.FooterMenu;
import com.example.gandy.exception.ResourceNotFoundException;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.footerMenu.FooterMenuServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/footerMenu")
public class FooterMenuController {
    @Autowired
    FooterMenuServiceImpl footerMenuService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody FooterMenu object) {
        footerMenuService.addObject(object);
        return ResponseEntity.ok("Information has been successfully registered.");
    }


    @PostMapping("/getById/{id}")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        System.out.println("dsfs");
        return ResponseEntity.ok(footerMenuService.getByIdObject(id));
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @PathVariable long id, @RequestBody FooterMenu object) {
        FooterMenu update = footerMenuService.getByIdObject(id);
        update.setName(object.getName());
        update.setCol(object.getCol());
        footerMenuService.updateObject(update);
        return ResponseEntity.ok(update);
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(footerMenuService.getAllObjects());
    }


    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        footerMenuService.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @PostMapping("/getFooterMenuByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getFooterMenuByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(footerMenuService.getFooterMenuByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }



}
