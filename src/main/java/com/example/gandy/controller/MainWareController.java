package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.MainWare;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.MainWareServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/mainWare")
public class MainWareController {
    @Autowired
    MainWareServiceImpl mainWareService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) {
        MainWare response = null;
        try {

            try {
                String fileName = fileStorageService.storeFile(file);
                ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            }catch (Exception e){
                e.getMessage();
            }
            response = objectMapper.readValue(jsonObject, MainWare.class);
            if (file!=null) {
                response.setImg(file.getOriginalFilename());
            }
            mainWareService.createObjects(response);
            return ResponseEntity.ok("MainWare registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        mainWareService.deleteObjects(id);
        return ResponseEntity.ok("mainWare removed successfully!");
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(mainWareService.getById(id));
    }


    @PostMapping("/main")
    public ResponseEntity<?> getMainMenu() {
        return ResponseEntity.ok(mainWareService.findObjects());
    }



    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(mainWareService.getAll(pageNumber));
    }


}
