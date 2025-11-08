package com.example.gandy.controller;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.*;
import com.example.gandy.payload.response.*;
import com.example.gandy.repo.FactorRepository;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.CartServiceImpl;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.service.ShipmentServiceImpl;
import com.example.gandy.service.WarrantyServiceImpl;
import com.example.gandy.utility.DiscountCalculation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartService;

    @Autowired
    FactorRepository factorRepository;

    @Autowired
    ProductCountServiceImpl productCountService;


    @Autowired
    ProductCountRepository productCountRepository;

    @Autowired
    ShipmentServiceImpl shipmentService;


    @Autowired
    WarrantyServiceImpl warrantyService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addCart(@Valid @RequestBody CartRequest cartRequest) {
        for (int i = 0; i < cartRequest.getList().size(); i++) {
            cartService.createObjects(cartRequest.getList().get(i));
        }
        return ResponseEntity.ok("add cart successfully");
    }


    @PostMapping("/getAllMyWares")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllMyWares() {
        List<Cart> cartList = new ArrayList<>();
        FactorResponseList factorResponseList = new FactorResponseList();
        List<FactorResponse> list = new ArrayList<>();

        List<Factor> factor = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;

        int finalPrice = 0;
        int totalDiscount = 0;
        int totalMainPrice = 0;
        String shipmentType = "";
        int shipment = 0;
        int result = 0;

        factor = factorRepository.findFactorByUserAndStatus(userDetails1.getId() , Status.CART);
        if (factor.size() > 0) {
            for (Factor factorItem : factor) {
                FactorResponse factorResponse = new FactorResponse();
                cartList = cartService.findCartsByFactorId(factorItem.getId());
                if (cartList.size() > 0) {
                    for (Cart item : cartList) {
                        finalPrice = 0;
                        totalDiscount = 0;
                        totalMainPrice = 0;

                        finalPrice += item.getFactorFinalPrice();
                        totalDiscount += item.getFactorDiscountVal();
                        totalMainPrice += item.getFactorMainPrice();
                    }
                    result = finalPrice;
                    result += (int) factorItem.getShipmentPrice();
                    factorResponse.setCartList(cartList);
                    factorResponse.setResult(result);
                    factorResponse.setShipmentType(factorItem.getShipment());
                    factorResponse.setFinalPrice(finalPrice);
                    factorResponse.setTotalDiscount(totalDiscount);
                    factorResponse.setShipment((int) factorItem.getShipmentPrice());
                    factorResponse.setTotalMainPrice(totalMainPrice);
                    list.add(factorResponse);
                }
            }
        }
        factorResponseList.setFactorResponseList(list);
        return ResponseEntity.ok(factorResponseList);
    }


    @PostMapping("/getAllWaresOfFactorByAdmin/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllWaresOfFactor(@PathVariable("id") long id) {
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
                return ResponseEntity.ok(factorResponseForAdmin);
            }
        }
        return ResponseEntity.ok(null);
    }


    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@Valid @RequestBody CartRequest cartRequest) {
        List<ProductCountBox> productCountBox = new ArrayList<>();
        int price = 0;
        int totalDiscount = 0;
        int totalMainPrice = 0;
        Warranty warranty = null;
        CartResponse result = new CartResponse();
        List<ProductCount> productCountList = new ArrayList<>();
        List<ProductCountResponse> productCountResponses = new ArrayList<>();
        for (int i = 0; i < cartRequest.getList().size(); i++) {
            productCountList.add(productCountService.findProductCount(cartRequest.getList().get(i).getProductCountId()));
            int val = productCountList.get(i).getCount() - cartRequest.getList().get(i).getCount();
            if (val < 0) {
                cartRequest.getList().get(i).setCount(productCountList.get(i).getCount());
            }
        }

        DiscountCalculation discountCalculation = new DiscountCalculation();
        productCountResponses = discountCalculation.calculation(productCountList);
        for (int i = 0; i < productCountResponses.size(); i++) {
            ProductCountBox productCountBox1 = new ProductCountBox();
            for (int j = 0; j < cartRequest.getList().size(); j++) {
                if (productCountResponses.get(i).getId() == cartRequest.getList().get(j).getProductCountId()) {
                    price += productCountResponses.get(i).getFinalPrice() * cartRequest.getList().get(j).getCount();
                    totalDiscount += productCountResponses.get(i).getDiscountVal() * cartRequest.getList().get(j).getCount();
                    totalMainPrice += productCountResponses.get(i).getPrice() * cartRequest.getList().get(j).getCount();
                    productCountBox1.setProductCountResponse(productCountResponses.get(i));
                    productCountBox1.setCount(cartRequest.getList().get(j).getCount());
                    productCountBox1.setWarranty(warrantyService.findWarranty(cartRequest.getList().get(j).getWarranty()));
                    productCountBox.add(productCountBox1);
                }
            }
        }
        result.setFinalPrice(price);
        result.setTotalMainPrice(totalMainPrice);
        result.setList(productCountBox);
        result.setTotalDiscount(totalDiscount);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/getAll")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@RequestBody ShipmentRequest shipment) {
        List<ProductCountBox> productCountBox = new ArrayList<>();
        int price = 0;
        int totalDiscount = 0;
        int totalMainPrice = 0;
        Shipment shipment1 = null;

        CartResponse result = new CartResponse();
        List<ProductCount> productCountList = new ArrayList<>();
        List<ProductCountResponse> productCountResponses = new ArrayList<>();
        List<Cart> cartList = cartService.getAll();

        for (Cart cart: cartList) {
            if (cart.getProductCount().getCount() < 1) {
                cartService.deleteObjects(cart.getId());
                cartList.remove(cart);
            }
        }

        for (int i = 0; i < cartList.size(); i++) {
            productCountList.add(cartList.get(i).getProductCount());
            int val = productCountList.get(i).getCount() - cartList.get(i).getCount();
            if (val < 0) {
                cartList.get(i).setCount(productCountList.get(i).getCount());
                RequestProduct requestProduct = new RequestProduct();
                requestProduct.setProductCountId(productCountList.get(i).getCount());
                requestProduct.setCartId(cartList.get(i).getId());
                requestProduct.setCount(productCountList.get(i).getCount());
                cartService.editCart(requestProduct);
            }
        }


        DiscountCalculation discountCalculation = new DiscountCalculation();
        productCountResponses = discountCalculation.calculation(productCountList);
        for (int i = 0; i < productCountResponses.size(); i++) {
            ProductCountBox productCountBox1 = new ProductCountBox();
            for (int j = 0; j < cartList.size(); j++) {
                if (productCountResponses.get(i).getId() == cartList.get(j).getProductCount().getId()) {
                    price += productCountResponses.get(i).getFinalPrice() * cartList.get(j).getCount();
                    totalDiscount += productCountResponses.get(i).getDiscountVal() * cartList.get(j).getCount();
                    totalMainPrice += productCountResponses.get(i).getPrice() * cartList.get(j).getCount();
                    productCountBox1.setProductCountResponse(productCountResponses.get(i));
                    productCountBox1.setCount(cartList.get(j).getCount());
                    productCountBox1.setCartId(cartList.get(j).getId());
                    productCountBox1.setWarranty(cartList.get(j).getWarranty());
                    productCountBox.add(productCountBox1);
                }
            }
        }
        if (shipment.getId() != 0) {
            shipment1 = shipmentService.findShipment(shipment.getId());
            result.setShipment(shipment1.getPrice());
            result.setFinalPrice(price + shipment1.getPrice());
        } else {
            result.setFinalPrice(price);
        }
        result.setTotalMainPrice(totalMainPrice);
        result.setList(productCountBox);
        result.setTotalDiscount(totalDiscount);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/editCart")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editCart(@Valid @RequestBody CartRequest cartRequest) {
        for (int i = 0; i < cartRequest.getList().size(); i++) {
            cartService.editCart(cartRequest.getList().get(i));
        }
        return ResponseEntity.ok("edit cart successfully");
    }


    @PostMapping("/findCartsByFactorId/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findCartsByFactorId(@PathVariable("id") int id) {
        return ResponseEntity.ok(cartService.findCartsByFactorId(id));
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCart(@PathVariable("id") int id) {
        cartService.deleteObjects(id);
        return ResponseEntity.ok("cart removed successfully!");
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findCart(@PathVariable("id") int id) {
        return ResponseEntity.ok(cartService.findObjects(id));
    }

    @PostMapping("/deleteAllCart/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAllCart() {
        cartService.deleteAllCart();
        return ResponseEntity.ok("cart removed successfully");
    }

}
