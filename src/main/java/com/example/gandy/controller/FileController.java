package com.example.gandy.controller;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Cart;
import com.example.gandy.entity.Factor;
import com.example.gandy.payload.response.FactorResponseForAdmin;
import com.example.gandy.repo.AddressRepository;
import com.example.gandy.repo.CartRepository;
import com.example.gandy.repo.FactorRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.*;
import com.example.gandy.utility.UserPDFExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/pdf")
public class FileController {


    @Autowired
    FactorServiceImpl factorService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    FactorRepository factorRepository;



    @GetMapping("/download/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void downloadInvoiceImage(@PathVariable("id") long id , HttpServletResponse response) throws Exception {
        FactorResponseForAdmin factorResponseForAdmin =  getAllWaresOfFactor(id);
        List<Address> address = addressRepository.findAddressByUsersId(factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getId());
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=factor.pdf";
        response.setHeader(headerKey, headerValue);
        UserPDFExporter exporter = new UserPDFExporter(factorResponseForAdmin , address.get(0));
        exporter.export(response);
    }



    @GetMapping("/myFactor/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void downloadMyFactor(@PathVariable("id") long id , HttpServletResponse response) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        Factor factor = null;
        factor = factorRepository.factorConvertToPdf(userDetails1.getId() , id);
        if (factor!=null){
            FactorResponseForAdmin factorResponseForAdmin =  getAllWaresOfFactor(id);
            List<Address> address = addressRepository.findAddressByUsersId(factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getId());

            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=factor.pdf";
            response.setHeader(headerKey, headerValue);
            UserPDFExporter exporter = new UserPDFExporter(factorResponseForAdmin , address.get(0));
            exporter.export(response);
        }
    }


    public FactorResponseForAdmin getAllWaresOfFactor(long id) {
        List<Cart> cartList = new ArrayList<>();
        Factor factor = null;

        int finalPrice = 0;
        int totalDiscount = 0;
        int totalMainPrice = 0;
        int result = 0;

        factor = factorRepository.findFactorById(id);
        if (factor != null) {
            FactorResponseForAdmin factorResponseForAdmin = new FactorResponseForAdmin();
            cartList = cartService.findCartsByFactorId(id);
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    finalPrice += item.getFactorFinalPrice();
                    totalDiscount += item.getFactorDiscountVal();
                    totalMainPrice += item.getFactorMainPrice();
                }
                result = finalPrice;
                result += (int) factor.getShipmentPrice();
                factorResponseForAdmin.setCartList(cartList);
                factorResponseForAdmin.setResult(result);
                factorResponseForAdmin.setShipmentType(factor.getShipment());
                factorResponseForAdmin.setFinalPrice((finalPrice));
                factorResponseForAdmin.setTotalDiscount(totalDiscount);
                factorResponseForAdmin.setShipment((int) factor.getShipmentPrice());
                factorResponseForAdmin.setTotalMainPrice((int) (totalMainPrice + factor.getShipmentPrice()));
                return factorResponseForAdmin;
            }
        }
        return null;
    }



}
