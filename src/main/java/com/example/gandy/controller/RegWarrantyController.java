package com.example.gandy.controller;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Warranty;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.RegWarrantyServiceImpl;
import com.example.gandy.service.WarrantyServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/regWarranty")
public class RegWarrantyController {

    @Autowired
    RegWarrantyServiceImpl regWarrantyService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;





    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject, @RequestParam("file") MultipartFile file) {
        RegWarranty response = null;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            response = objectMapper.readValue(jsonObject, RegWarranty.class);
            response.setImage(file.getOriginalFilename());
            regWarrantyService.createObjects(response);
            return ResponseEntity.ok("RegWarranty registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/findRegWarrantyByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findWarrantyByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(regWarrantyService.findRegWarrantyByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }



    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok( regWarrantyService.getAll());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( regWarrantyService.findAll(pageNumber));
    }


    @PostMapping("/getById/{id}")
    public ResponseEntity<?> getByid(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( regWarrantyService.findWarranty(id));
    }



    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        regWarrantyService.deleteObjects(id);
        return ResponseEntity.ok("Warranty removed successfully!");
    }
}
