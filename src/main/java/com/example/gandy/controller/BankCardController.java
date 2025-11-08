package com.example.gandy.controller;

import com.example.gandy.entity.BankCard;
import com.example.gandy.payload.request.BankCardRequest;
import com.example.gandy.service.BankCardServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankCard")
public class BankCardController {
    @Autowired
    BankCardServiceImpl service;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody BankCardRequest object) {
        service.addObject(object);
        return ResponseEntity.ok("Information has been successfully registered.");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody BankCardRequest object) {
        BankCard getBankCard = null;
        getBankCard = service.getByIdObject(object.getId());
        getBankCard.setCardNum(object.getCardNum());
        getBankCard.setName(object.getName());
        getBankCard.setFamily(object.getFamily());
        getBankCard.setShaba(object.getShaba());
        service.update(getBankCard);
        return ResponseEntity.ok("Information has been successfully registered.");
    }

    @PostMapping("/getById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getById(@Valid @PathVariable long id) {
        return ResponseEntity.ok(service.getByIdObject(id));
    }

    @PostMapping("/getAll")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAllObjects());
    }

    @PostMapping("/getAll/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllByUserId(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(service.getAllByUserId(userId));
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@Valid @PathVariable("id") long id) {
        service.deleteByIdObject(id);
        return ResponseEntity.ok("The desired item was successfully deleted!");
    }

}
