package com.example.gandy.controller;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Shipment;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.RegWarrantyServiceImpl;
import com.example.gandy.service.ShipmentServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentController {

    @Autowired
    ShipmentServiceImpl shipmentService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;





    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject, @RequestParam("file") MultipartFile file) {
        Shipment response = null;
        try {
            String fileName = fileStorageService.storeFile(file);
            ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            response = objectMapper.readValue(jsonObject, Shipment.class);
            response.setImage(file.getOriginalFilename());
            shipmentService.createObjects(response);
            return ResponseEntity.ok("shipment registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/findShipmentByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findShipmentByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(shipmentService.findShipmentByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }



    @PostMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(shipmentService.getAll());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok( shipmentService.findAll(pageNumber));
    }


    @PostMapping("/getById/{id}")
    public ResponseEntity<?> getByid(@PathVariable("id") int id) throws IOException {
        return ResponseEntity.ok( shipmentService.findShipment(id));
    }



    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) throws IOException {
        shipmentService.deleteObjects(id);
        return ResponseEntity.ok("shipment removed successfully!");
    }
}
