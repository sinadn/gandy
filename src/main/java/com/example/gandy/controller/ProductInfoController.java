package com.example.gandy.controller;

import com.example.gandy.entity.BankCard;
import com.example.gandy.entity.ProductInfo;
import com.example.gandy.payload.request.BankCardRequest;
import com.example.gandy.service.BankCardServiceImpl;
import com.example.gandy.service.ProductInfoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pInfo")
public class ProductInfoController {
    @Autowired
    ProductInfoServiceImpl service;


    @PostMapping("/getById/{id}")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(service.findProductInfoById(id));
    }

    @PostMapping("/getByProductId/{pId}")
    public ResponseEntity<?> getAllByUserId(@PathVariable("pId") long pId) {
        ProductInfo productInfo = service.findProductInfoByProductId(pId);
        if (productInfo!=null){
            return ResponseEntity.ok(productInfo);
        }else {
            return ResponseEntity.ok(new ProductInfo());

        }
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable("id") long id) {
        service.deleteProductInfo(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }

}
