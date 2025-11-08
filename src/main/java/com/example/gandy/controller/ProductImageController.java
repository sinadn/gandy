package com.example.gandy.controller;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.ERole;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.entity.Role;
import com.example.gandy.exception.DeleteImageException;
import com.example.gandy.exception.ResourceNotFoundException;
import com.example.gandy.exception.TokenRefreshException;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.ProductImageRepository;
import com.example.gandy.repo.RoleRepository;
import com.example.gandy.service.productImage.ProductImageServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.NameBuilder;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productImage")
public class ProductImageController {

    @Autowired
    ProductImageRepository repository;

    @Autowired
    ProductImageServiceImpl service;

    @Autowired
    RoleRepository roleRepository;



    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        ProductImage response;

        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, ProductImage.class);
        if (file!=null) {
            response.setImg(file.getOriginalFilename());
        }
        service.addObject(response);
        return ResponseEntity.ok("ProductImage has been registered successfully.");
    }



//    @PostMapping("/uploadimg")
//    public ResponseEntity<?> uploadImage(@Nullable @RequestParam("files") MultipartFile[] files) throws JsonProcessingException {
//        ProductImage response;
//        String message = "";
//        try {
//            List<String> fileNames = new ArrayList<>();
//            Arrays.asList(files).forEach(file -> {
//                fileStorageService.storeFile(file);
//                fileNames.add(file.getOriginalFilename());
//            });
//            message = "Uploaded the files successfully: " + fileNames;
//            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the files successfully");
//        } catch (Exception e) {
//            message = "Fail to upload files!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed to upload files");
//        }
//    }




//    @PostMapping("/uploadimg")
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<?> uploadimg(@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
//        ProductImage response;
//
//        try {
//            String fileName = fileStorageService.storeFile(file);
//            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
//        }catch (Exception e){
//            e.getMessage();
//        }
//        response = objectMapper.readValue(jsonObject, ProductImage.class);
//        if (file!=null) {
//            response.setImg(file.getOriginalFilename());
//        }
//        service.addObject(response);
//        return ResponseEntity.ok("ProductImage has been registered successfully.");
//    }



    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestParam("model") String jsonObject,@Nullable @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        ProductImage response;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        }catch (Exception e){
            e.getMessage();
        }
        response = objectMapper.readValue(jsonObject, ProductImage.class);
        repository.findById(response.getId())
                .map(item -> {
                    item.setImg(response.getImg());
                    item.setProduct(response.getProduct());
                    if (file!=null) {
                        item.setImg(file.getOriginalFilename());
                    }
                    service.addObject(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    if (file!=null) {
                        response.setImg(file.getOriginalFilename());
                    }
                    service.addObject(response);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Cover registered successfully!");
    }



    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.getById(id));
    }



    @PostMapping("/getProductImageByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getProductImageByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(service.getProductImageByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @GetMapping("/getByProductId/{id}")
    public List<?> getByProductId(@Valid @PathVariable long id) {
        return service.findByProductId(id);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @PathVariable long id, @RequestBody ProductImage object) {
        ProductImage update = service.getByIdObject(id).orElseThrow(() -> new ResourceNotFoundException("ProductImage not exist with id: " + id));
        update.setProduct(object.getProduct());
        update.setImg(object.getImg());
        service.updateObject(update);
        return ResponseEntity.ok(update);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllObjects());
    }


    @PostMapping("/getAllProductImage/{pageNumber}")
    public ResponseEntity<?> getAllProductImage(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(service.getAllProductImage(pageNumber));
    }


    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable long id) {
        try {
            ProductImage productImage = service.getById(id);
            fileStorageService.delete(productImage.getImg());
            service.deleteByIdObject(productImage.getId());
            return ResponseEntity.ok("The desired item was successfully deleted!");
        }catch (Exception e){
           throw  new DeleteImageException("delete Error",
                    "The desired item was not deleted!");

        }
    }



    @DeleteMapping("/files/{id}/{filename:.+}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable long id, @PathVariable String filename) {
        String message = "";

        try {
            boolean existed = fileStorageService.delete(filename);
            service.deleteImageField(id);
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
