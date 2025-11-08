package com.example.gandy.service;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.FactorRequest;
import com.example.gandy.payload.request.RequestProduct;
import com.example.gandy.repo.CartRepository;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.repo.UsersRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class CartServiceImpl {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductCountRepository productCountRepository;

    @Autowired
    FactorServiceImpl factorService;

    @Autowired
    WarrantyServiceImpl warrantyService;



    public void createObjects(RequestProduct objects) {
        Cart cart = new Cart();
        Factor factor = new Factor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime create_at = LocalDateTime.parse(formatDateTime, formatter);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        try {
            cart = findObjectByFactor(userDetails1.getMobile(),objects.getProductCountId());
        }catch (Exception e){
            e.getMessage();
        }

        if (cart==null) {
            cart=new Cart();
            cart.setCount((short) 1);
            ProductCount productCount = new ProductCount();
            productCount=productCountRepository.findByProdoctCountId(objects.getProductCountId());
            if (productCount==null)
                return;
            cart.setProductCount(productCount);
            cart.setWarranty(warrantyService.findWarranty(objects.getWarranty()));
            cart.setCreate_at(create_at);
            try {
                factor = factorService.findFactorByUserId(userDetails1.getId());
                if (factor != null) {
                    cart.setFactor(factor);
                    cartRepository.save(cart);
                } else {
                    Payment payment = new Payment();
                    payment.setStatus(Status.CART);
                    FactorRequest factorRequest = new FactorRequest();
                    factorRequest.setPayment(payment);
                    factorRequest.setUsers(userDetails1.getId());
                    factorService.createObjects(factorRequest);
                    cart.setFactor(factorService.findFactorByUserId(userDetails1.getId()));
                    cartRepository.save(cart);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }


    public void editCart(RequestProduct objects) {
        Cart cart = new Cart();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update_at = LocalDateTime.parse(formatDateTime, formatter);
        cart =  findObjects(objects.getCartId());
        cart.setCount(objects.getCount());
        cart.setUpdate_at(update_at);
         cartRepository.save(cart);
    }

    public List<Cart> getAll() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        return cartRepository.findByfactor(userDetails1.getMobile() , Status.CART);
    }

    public void deleteObjects(long id) {
        cartRepository.deleteById((int) id);
    }

    public Cart findObjectByFactor(String mobile,long productCountId) {
        return cartRepository.findObjectByFactor(mobile,productCountId,Status.CART);
    }
    public Cart findObjects(long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        return cartRepository.findCartByIdAndFactorUsersMobile(id,userDetails1.getMobile());
    }




    public List<Cart> findCartsByFactorId(long id) {
        return cartRepository.findCartsByFactorId(id);
    }

    public List<Cart> findCartsByPaymentAuthority(String authority) {
        return cartRepository.findCartsByPaymentAuthority(authority);
    }




    public void deleteAllCart() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
         cartRepository.deleteCartByFactor_Users_Mobile(userDetails1.getMobile());
    }

}
