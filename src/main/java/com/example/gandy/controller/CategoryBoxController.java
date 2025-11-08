package com.example.gandy.controller;

import com.example.gandy.entity.CategoryBox;
import com.example.gandy.entity.Cover;
import com.example.gandy.repo.CategoryBoxRepository;
import com.example.gandy.repo.CoverRepository;
import com.example.gandy.service.CategoryBoxServiceImpl;
import com.example.gandy.service.CoverServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/api/categoryBox")
public class CategoryBoxController {


    @Autowired
    CategoryBoxRepository repository;

    @Autowired
    CategoryBoxServiceImpl service;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestParam("model") String jsonObject, @Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        CategoryBox response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, CategoryBox.class);
        if (file != null) {
            response.setImg(file.getOriginalFilename());
        }
        service.add(response);
        return ResponseEntity.ok("categoryBox registered successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject, @Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        CategoryBox response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        } catch (Exception e) {
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, CategoryBox.class);
        service.update(response, file);
        return ResponseEntity.ok("categoryBox registered successfully!");
}


    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @PostMapping("/getByid/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findCover(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok(service.getById(id));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        CategoryBox categoryBox = service.getById(id);
        boolean existed = false;
        try {
            existed = fileStorageService.delete(categoryBox.getImg());
        } catch (Exception e) {
            e.getMessage();
        }
        if (existed) {
            System.out.println("File deleted");
        } else {
            System.out.println("File does not exist");
        }
        service.delete(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @DeleteMapping("/files/{filename:.+}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        String message = "";

        try {
            boolean existed = fileStorageService.delete(filename);
            service.deleteCategoryBoxByImgName(filename);

            if (existed) {
                message = "Delete the file successfully: " + filename;
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
            message = "The file does not exist!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } catch (Exception e) {
            message = "Could not delete the file: " + filename + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

}
