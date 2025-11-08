package com.example.gandy.controller;

import com.example.gandy.entity.Address;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.repo.AddressRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.AddressServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    AddressRepository repository;

    @Autowired
    AddressServiceImpl addressServiceImpl;


    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> add(@Valid @RequestBody AddressInfoRequest addressInfoRequest) {
        addressServiceImpl.addObject(addressInfoRequest);
        System.out.println("add address");
        return ResponseEntity.ok("add address successfully");
    }


    @PutMapping("/updateByUser")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateByUser(@RequestBody Address address) {
        Address address1 = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        address1 = repository.findAddressByIdAndUsersMobile(address.getId(), userDetails1.getMobile());
        address1.setProvince(address.getProvince());
        address1.setAddress(address.getAddress());
        address1.setNo(address.getNo());
        address1.setUnit(address.getUnit());
        address1.setArea(address.getArea());
        address1.setCity(address.getCity());
        address1.setUsers(address.getUsers());
        address1.setPostalCode(address.getPostalCode());
        addressServiceImpl.addObjectByAdmin(address1);
        return ResponseEntity.ok("Product updated successfully!");
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Address address) {
        repository.findById(address.getId())
                .map(item -> {
                    item.setProvince(address.getProvince());
                    item.setAddress(address.getAddress());
                    item.setNo(address.getNo());
                    item.setUnit(address.getUnit());
                    item.setArea(address.getArea());
                    item.setCity(address.getCity());
                    item.setUsers(address.getUsers());
                    item.setPostalCode(address.getPostalCode());
                    addressServiceImpl.addObjectByAdmin(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    addressServiceImpl.addObjectByAdmin(address);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/addAddress")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addAddress(@Valid @RequestBody Address address) {
        addressServiceImpl.addObjectByAdmin(address);
        return ResponseEntity.ok("add address successfully");
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") long id) {
        addressServiceImpl.deleteAddress(id);
        return ResponseEntity.ok("address removed successfully!");
    }


    @DeleteMapping("/deleteAddress/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAdd(@PathVariable("id") long id) {
        addressServiceImpl.deleteAdd(id);
        return ResponseEntity.ok("address removed successfully!");
    }


    @PostMapping("/getAddresses")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddresses() {
        return ResponseEntity.ok(addressServiceImpl.findObjects());
    }


    @PostMapping("/getAddressList/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddressList(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(addressServiceImpl.findAddressList(pageNumber));
    }


    @PostMapping("/getAddressListByMobile/{mobile}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddressListByMobile(@PathVariable("mobile") String mobile) {
        return ResponseEntity.ok(addressServiceImpl.getAddressListByMobile(mobile));
    }

    @PostMapping("/getAddress/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAddress(@PathVariable("id") long id) {
        return ResponseEntity.ok(addressServiceImpl.getObject(id));
    }


    @PostMapping("/findAddress/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findAddress(@PathVariable("id") long id) {
        return ResponseEntity.ok(addressServiceImpl.findAddress(id));
    }


}
