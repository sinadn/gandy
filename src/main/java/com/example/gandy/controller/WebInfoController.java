package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.WebInfo;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.webInfo.WebInfoServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/webInfo")
public class WebInfoController {
    @Autowired
    WebInfoServiceImpl service;


    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) {

        WebInfo response = null;
        try {

            try {
                String fileName = fileStorageService.storeFile(file);
                ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();

            }catch (Exception e){
                e.getMessage();
            }
            response = objectMapper.readValue(jsonObject, WebInfo.class);
            if (file!=null) {
                response.setLogo(file.getOriginalFilename());
            }
            response.setId(1L);



            service.addObject(response);
            return ResponseEntity.ok("webInfo has been registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/getById/{id}")
    public WebInfo getById(@Valid @PathVariable long id) {
        return service.getByIdObject(id);
    }



    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        service.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was deleted successfully!");
    }


    @PostMapping("/getAll")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


}
