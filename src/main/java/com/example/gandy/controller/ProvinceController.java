package com.example.gandy.controller;

import com.example.gandy.entity.Province;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.service.ProvinceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {

@Autowired
    ProvinceServiceImpl provinceService;

    @PostMapping( "/all")
    public ResponseEntity<?> getProvince() {
        return ResponseEntity.ok( provinceService.getObjects());
    }


    @PostMapping("/getProvinceByWords")
    public ResponseEntity<?> getProvinceByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(provinceService.findProvinceByWords(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

}
