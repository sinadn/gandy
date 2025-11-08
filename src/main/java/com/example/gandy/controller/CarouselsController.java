package com.example.gandy.controller;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.Carousels;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.ArticleServiceImpl;
import com.example.gandy.service.CarouselsServiceImpl;
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

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/carousel")
public class CarouselsController {

    @Autowired
    CarouselsServiceImpl carouselsService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody Carousels carousels) {
        carouselsService.create(carousels);
        return ResponseEntity.ok("add carousels successfully");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Carousels carousels) {
        carouselsService.update(carousels);
        return ResponseEntity.ok("update carousels successfully");
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        carouselsService.deleteObjects(id);
        return ResponseEntity.ok("carousels removed successfully!");
    }


    @PostMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(carouselsService.getAll());
    }


//    @PostMapping("/searchCarousl/{name}")
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<?> getArticleByWords(@PathVariable("name") String name) {
//            return ResponseEntity.ok(carouselsService.searchCarouselByProductTypeName(name));
//    }

}
