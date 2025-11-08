package com.example.gandy.controller;

import com.example.gandy.entity.Province;
import com.example.gandy.service.CityServiceImpl;
import com.example.gandy.service.ProvinceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/city")
public class CityController {

@Autowired
CityServiceImpl cityService;

    @PostMapping( "/{provinceId}/")
    public ResponseEntity<?> getProvince(@PathVariable("provinceId") int provinceId)
    {return ResponseEntity.ok( cityService.getObjects(provinceId));}

}
