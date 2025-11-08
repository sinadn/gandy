package com.example.gandy.controller;

import com.example.gandy.entity.Activation;
import com.example.gandy.service.ActivationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/activation")
public class ActivationController {
    @Autowired
    ActivationServiceImpl activationService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Activation object) {
        Random rand = new Random();
        int int_random = rand.nextInt(999999);
        System.out.println(int_random);
        activationService.addObject(object);
        return ResponseEntity.ok("Information has been successfully registered.");
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<?> getById(@Valid @PathVariable long id) {
        return activationService.getByIdObject(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(activationService.getAllObjects());
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        activationService.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }

}
