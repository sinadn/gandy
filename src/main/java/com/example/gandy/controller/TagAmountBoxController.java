package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Cover;
import com.example.gandy.entity.TagAmountBox;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.TagAmountRepository;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.TagAmountBoxServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
@RestController
@RequestMapping("/api/TagAmountBox")
public class TagAmountBoxController {
    @Autowired
    TagAmountBoxServiceImpl service;

    @Autowired
    TagAmountRepository repository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        TagAmountBox response;

        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }

        response = objectMapper.readValue(jsonObject, TagAmountBox.class);
        if (file!=null) {
            response.setImage(file.getOriginalFilename());
        }

        int size =service.countTagAmountBoxByBox(response.getBox());
        if (size<6 && (response.getBox()>=1 && response.getBox()<=6))
             service.createObjects(response);
        return ResponseEntity.ok("add tagAmountBox successfully");
    }




    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        TagAmountBox response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, TagAmountBox.class);
        repository.findById(response.getId())
                .map(item -> {
                    item.setAmount(response.getAmount());
                    item.setBox(response.getBox());
                    item.setName(response.getName());
                    item.setNum(response.getNum());
                    item.setProductType(response.getProductType());
                    item.setProductTag(response.getProductTag());
                    item.setImage(response.getImage());
                    if (file!=null) {
                        item.setImage(file.getOriginalFilename());
                    }
                    service.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    if (file!=null) {
                        response.setImage(file.getOriginalFilename());
                    }
                    service.createObjects(response);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Cover registered successfully!");
    }



    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        TagAmountBox tagAmountBox = service.getById(id);
        boolean existed=false;
        try {
            existed = fileStorageService.delete(tagAmountBox.getImage());
        }catch (Exception e){
            e.getMessage();
        }
        if (existed) {
            System.out.println("File deleted");
        } else {
            System.out.println("File does not exist");
        }
        service.deleteObjects(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }



    @DeleteMapping("/files/{filename:.+}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        String message = "";

        try {
            boolean existed = fileStorageService.delete(filename);
            service.deleteCoverByImgName(filename);

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


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PostMapping("/getAll")
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


}
