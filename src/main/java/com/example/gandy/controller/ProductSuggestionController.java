package com.example.gandy.controller;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.ProductSugRequest;
import com.example.gandy.payload.request.PsugRequest;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.service.ProductServiceImpl;
import com.example.gandy.service.productSuggestion.ProductSuggestionServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/psug")
public class ProductSuggestionController {
    @Autowired
    ProductSuggestionServiceImpl productSuggestionService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ProductCountServiceImpl productCountService;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getMainMenu(@RequestBody ProductSugRequest productSugRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime create = LocalDateTime.parse(productSugRequest.getCreate_at(), formatter);
        LocalDateTime expire = LocalDateTime.parse(productSugRequest.getExpire_at(), formatter);
        ProductSuggestion productSuggestion = new ProductSuggestion();
        List<ProductCount> productCountList = new ArrayList<>();
        try {
            productCountList= productCountService.findProduct(productSugRequest.getProduct().getId());
        }catch (Exception e){
            e.getMessage();
        }

        if (productCountList.size()>0){
            if (productSugRequest.getId()!=0){
                productSuggestion.setId(productSugRequest.getId());
                for (ProductCount item : productCountList) {
                    if (item.getMain()==1){
                        productSuggestion.setProductCount(item);
                    }
                }
                productSuggestion.setExpire_at(expire);
                productSuggestion.setCreate_at(create);
                productSuggestionService.addObject(productSuggestion);

            }else {
                for (ProductCount item : productCountList) {
                    if (item.getMain()==1){
                        productSuggestion.setProductCount(item);
                    }
                }
                productSuggestion.setExpire_at(expire);
                productSuggestion.setCreate_at(create);
                productSuggestionService.addObject(productSuggestion);
            }
        }
        return ResponseEntity.ok("Product register successfully!");
    }

    @PostMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( productSuggestionService.getAllObjects(pageNumber));
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getpsug() {
        return ResponseEntity.ok( productSuggestionService.getpsug());
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductSuggestion(@PathVariable("id") long id) {
        return ResponseEntity.ok(productSuggestionService.findObject(id));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delMenu(@RequestBody ProductRequest productRequest) {
        productSuggestionService.deleteByIdObject(productRequest.getId());
        return ResponseEntity.ok("Product removed successfully!");
    }
}
