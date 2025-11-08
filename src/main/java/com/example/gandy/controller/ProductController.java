package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.entity.ProductInfo;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.response.ProductResponse;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.repo.ProductRepository;
import com.example.gandy.service.CategoryServiceImpl;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.service.ProductInfoServiceImpl;
import com.example.gandy.service.ProductServiceImpl;
import com.example.gandy.service.productImage.ProductImageServiceImpl;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ProductInfoServiceImpl productInfoService;

    @Autowired
    ProductRepository repository;

    @Autowired
    ProductCountServiceImpl productCountService;


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ProductImageServiceImpl productImageService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getMainMenu(@RequestParam("model") String jsonObject, @Nullable @RequestParam("files") MultipartFile[] files) {
        ProductRequest productRequest;
        Product product = null;
        try {
            productRequest = objectMapper.readValue(jsonObject, ProductRequest.class);
            product = productService.createObjects(productRequest);
            productImageService.uploadimg(product.getId(), files);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProduct(product);
            productInfo.setAttributes(productRequest.getAttributes());
            productInfoService.addObject(productInfo);
            productCountService.addProductCounts(productRequest.getPCountList() , product);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the files successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed to upload files");
        }
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject, @Nullable @RequestParam("files") MultipartFile[] files) {
        ProductRequest productRequest = null;
        Product product = null;
        try {
            productRequest = objectMapper.readValue(jsonObject, ProductRequest.class);
            product = productService.updateObjects(productRequest);
            productImageService.uploadimg(product.getId(), files);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProduct(product);
            productInfo.setAttributes(productRequest.getAttributes());
            productInfoService.updateObject(productInfo);
            productCountService.updateProductCounts(productRequest.getPCountList() , product);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the files successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed to upload files");
        }
    }


    @PostMapping("/getAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(productService.getAll(pageNumber));
    }


    @PostMapping("/getProductByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(productService.findProductByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        ProductResponse productResponse = new ProductResponse();
        ProductInfo productInfo = new ProductInfo();
        Product product = productService.getById(id);
        productInfo = productInfoService.findProductInfoByProductId(product.getId());
        productResponse.setProductInfo(productInfo);
        productResponse.setProduct(product);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delMenu(@RequestBody ProductRequest productRequest) {
        productService.deleteObjects(productRequest.getId());
        return ResponseEntity.ok("Product removed successfully!");
    }
}
