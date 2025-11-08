package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Cover;
import com.example.gandy.entity.Guarantee;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.service.CoverServiceImpl;
import com.example.gandy.service.GuaranteeServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.NameBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/api/guarantee")
public class GuaranteeController {

    @Autowired
    GuaranteeServiceImpl guaranteeService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")

    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject, @RequestParam("file") MultipartFile file) {
        Guarantee response = null;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            response = objectMapper.readValue(jsonObject, Guarantee.class);
            response.setImage(file.getOriginalFilename());
            guaranteeService.createObjects(response);
            return ResponseEntity.ok("Guarantee registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok( guaranteeService.findAll());
    }


    @PostMapping("/getByid/{id}")
    public ResponseEntity<?> findCover(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( guaranteeService.findGuarantee(id));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        guaranteeService.deleteObjects(id);
        return ResponseEntity.ok("guarantee removed successfully!");
    }
}
