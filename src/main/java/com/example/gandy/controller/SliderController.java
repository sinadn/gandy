package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Slider;
import com.example.gandy.exception.ResourceNotFoundException;
import com.example.gandy.service.slider.SliderServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.NameBuilder;
import com.example.gandy.utils.uploadFile.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/slider")
public class SliderController {
    @Autowired
    SliderServiceImpl service;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;



    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        Slider response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, Slider.class);
        if (file!=null) {
            response.setImage(file.getOriginalFilename());
        }
        service.addObject(response);
        return ResponseEntity.ok("slider registered successfully!");
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") long id) throws IOException {
        return ResponseEntity.ok( service.getByIdObject(id));
    }


    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllObjects());
    }

    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) throws IOException {
        Slider slider = service.getByIdObject(id);
        boolean existed = fileStorageService.delete(slider.getImage());


        if (existed) {
            System.out.println("File deleted");
        } else {
            System.out.println("File does not exist");
        }
        service.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }



    @DeleteMapping("/files/{filename:.+}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        String message = "";

        try {
            boolean existed = fileStorageService.delete(filename);
            service.deleteSliderByImgName(filename);

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
