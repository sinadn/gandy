package com.example.gandy.controller;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.ProductSuggestion;
import com.example.gandy.entity.Slider;
import com.example.gandy.exception.ResourceNotFoundException;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.ProductSugRequest;
import com.example.gandy.payload.request.PsugRequest;
import com.example.gandy.repo.CoverRepository;
import com.example.gandy.service.CoverServiceImpl;
import com.example.gandy.service.ProductServiceImpl;
import com.example.gandy.service.productSuggestion.ProductSuggestionServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.NameBuilder;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/cover")
public class CoversController {


    @Autowired
    CoverRepository repository;


    @Autowired
    CoverServiceImpl coverService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Cover response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, Cover.class);
        if (file!=null) {
            response.setImage(file.getOriginalFilename());
        }
        coverService.createObjects(response);
        return ResponseEntity.ok("Cover registered successfully!");
    }




    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Cover response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, Cover.class);
        repository.findById(response.getId())
                .map(item -> {
                    item.setAmount(response.getAmount());
                    item.setProductType(response.getProductType());
                    item.setUrl(response.getUrl());
                    item.setAttributeOption(response.getAttributeOption());
                    item.setCol(response.getCol());
                    item.setProductTag(response.getProductTag());
                    item.setImage(response.getImage());
                    item.setPosition(response.getPosition());
                    if (file!=null) {
                        item.setImage(file.getOriginalFilename());
                    }
                    coverService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    if (file!=null) {
                        response.setImage(file.getOriginalFilename());
                    }
                    coverService.createObjects(response);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Cover registered successfully!");
    }











    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok( coverService.findAll());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(coverService.findAllByPagination(pageNumber));
    }


    @PostMapping("/getByid/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findCover(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( coverService.findCover(id));
    }

    @PostMapping("/getByPosition/{position}")
    public ResponseEntity<?> getByPosition(@PathVariable("position") int position) throws IOException {
        return ResponseEntity.ok( coverService.findCoversByPosition(position));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        Cover cover = coverService.findCover(id);
        boolean existed=false;
        try {
             existed = fileStorageService.delete(cover.getImage());
        }catch (Exception e){
            e.getMessage();
        }
        if (existed) {
            System.out.println("File deleted");
        } else {
            System.out.println("File does not exist");
        }
        coverService.deleteObjects(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }


    @DeleteMapping("/files/{filename:.+}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        String message = "";

        try {
            boolean existed = fileStorageService.delete(filename);
            coverService.deleteCoverByImgName(filename);

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
