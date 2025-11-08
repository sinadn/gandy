package com.example.gandy.controller;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.AttributeType;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.response.AttributeOptionResponse;
import com.example.gandy.payload.response.AttributeTypeResponse;
import com.example.gandy.payload.response.PropertyResponse;
import com.example.gandy.repo.AttributeOptionRepository;
import com.example.gandy.service.AttributeOptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/attributeOption")
public class AttributeOptionController {

    @Autowired
    AttributeOptionServiceImpl attributeOptionService;

    @Autowired
    AttributeOptionRepository repository;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody AttributeOption attributeOption) {
        attributeOptionService.createObjects(attributeOption);
        return ResponseEntity.ok("attributeOption register successfully!");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody AttributeOption attributeOption) {

        repository.findById(attributeOption.getId())
                .map(item -> {
                    item.setAttributeOption(attributeOption.getAttributeOption());
                    item.setAttributeType(attributeOption.getAttributeType());
                    item.setFilterName(attributeOption.getFilterName());
                    attributeOptionService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    attributeOptionService.createObjects(attributeOption);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getAttributeOptionByWords")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAttributeTypeByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(attributeOptionService.getAttributeOptionByWords(productRequest.getName(), productRequest.getId()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/findAttributeOptions/{ptId}")
    public ResponseEntity<?> getAttributeOptions(@PathVariable("ptId") long ptId) {
        PropertyResponse propertyResponse = new PropertyResponse();
        List<AttributeTypeResponse> attributeTypeResponseList = new ArrayList<>();
        List<AttributeOption> attributeOptionList = new ArrayList<>();

        attributeOptionList = attributeOptionService.findAttributeOptions(ptId);

        HashSet<AttributeType> attributeTypeList = new HashSet<>();
        for (AttributeOption item : attributeOptionList) {
            attributeTypeList.add(item.getAttributeType());
        }

        for (AttributeType item : attributeTypeList) {
            AttributeTypeResponse attributeTypeResponse = new AttributeTypeResponse();
            attributeTypeResponse.setItem(item);
            attributeTypeResponseList.add(attributeTypeResponse);
        }


        for (AttributeTypeResponse attributeTypeResponse : attributeTypeResponseList) {
            List<AttributeOptionResponse> list = new ArrayList<>();
            for (AttributeOption attributeOption : attributeOptionList) {
                if (attributeOption.getAttributeType().getId().equals(attributeTypeResponse.getItem().getId())) {
                    AttributeOptionResponse attributeOptionResponse1 = new AttributeOptionResponse();
                    attributeOptionResponse1.setAttributeOption(attributeOption.getAttributeOption());
                    attributeOptionResponse1.setId(attributeOption.getId());
                    if (!Objects.equals(attributeOption.getFilterName(), "")) {
                        attributeOptionResponse1.setFilterName(attributeOption.getFilterName());
                    }
                    list.add(attributeOptionResponse1);
                }
            }
            attributeTypeResponse.setList(list);
        }
        propertyResponse.setPropertylist(attributeTypeResponseList);
        return ResponseEntity.ok(propertyResponse);
    }


    @PostMapping("/getAttributeOptionByAT/{atId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAttributeTypeByAT(@PathVariable("atId") long atId) {
        return ResponseEntity.ok(attributeOptionService.getAttributeOptionByAT(atId));
    }


    @PostMapping("/searchAttributeOption")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> searchAttributeOption(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            return ResponseEntity.ok(attributeOptionService.searchAttributeOption(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/findAll/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(attributeOptionService.getAllAttributeOption(pageNumber));
    }


    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getByid(@PathVariable("id") long id) throws IOException {
        return ResponseEntity.ok(attributeOptionService.findObject(id));
    }


    @PostMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) throws IOException {
        attributeOptionService.deleteByIdObject(id);
        return ResponseEntity.ok("attributeOption removed successfully!");
    }
}
