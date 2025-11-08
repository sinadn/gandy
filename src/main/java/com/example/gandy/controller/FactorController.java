package com.example.gandy.controller;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.AdminFactorRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.UserFactorRequest;
import com.example.gandy.payload.response.ProductCountResponse;
import com.example.gandy.repo.*;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.BankCardServiceImpl;
import com.example.gandy.service.FactorServiceImpl;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.utility.DiscountCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/factor")
public class FactorController {
    @Autowired
    FactorServiceImpl factorService;

    @Autowired
    BankCardServiceImpl bankCardService;

    @Autowired
    CartRepository cartRepository;




    @Autowired
    ShipmentStatusRepository shipmentStatusRepository;

    @Autowired
    ProductCountServiceImpl productCountService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ShipmentRepository shipmentRepository;


    @PutMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody UserFactorRequest factor) {
        Factor factor1 = null;
        List<Cart> cartList = null;
        try {
            factor.setAddress(addressRepository.findAddressById(factor.getAddress().getId()));
            factor.setShipment(shipmentRepository.findShipmentById(factor.getShipment().getId()));
        }catch (Exception e){

        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime create_at = LocalDateTime.parse(formatDateTime, formatter);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        try {
            factor1 = factorService.findFactorByUsersAAndPaymentStatus(Status.CART);
        } catch (Exception e) {
            e.getMessage();
        }
        if (factor1 != null) {
            factor1.setUsers(usersRepository.findUsersById(userDetails1.getId()));
            factor1.setAddress(factor.getAddress().getProvince().getName() + " ," + factor.getAddress().getCity().getName() + "," +  factor.getAddress().getAddress());
            factor1.setShipment(factor.getShipment().getName());
            factor1.setShipmentPrice(factor.getShipment().getPrice());
            factor1.setUpdate_at(create_at);
        }
            factorService.addObject(factor1);
        return ResponseEntity.ok("factor is updated!");

    }




    @PutMapping("/cancelMyFactor/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> cancelMyFactor(@PathVariable("id") long id) {
        Factor factor=null;
        List<BankCard> list = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;

        list = bankCardService.getAllObjects();
        if (list.size()<=0){
            return ResponseEntity.ok(false);
        }
        factor = factorService.findByfactorIdAndUserId(id , userDetails1.getId());
        Payment payment = new Payment();
        payment= factor.getPayment();
        payment.setStatus(Status.RETURN);
        factor.setPayment(payment);

        Set<ShipmentStatus> shipmentStatuses = new HashSet<>();
        ShipmentStatus shipmentStatus = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_RETURN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        sendSms(userDetails1.getMobile(), 8);
        shipmentStatuses.add(shipmentStatus);
        factor.setShipmentStatus(shipmentStatuses);
        factorService.addObject(factor);
        return ResponseEntity.ok("factor is updated!");
    }

    @PutMapping("/updateByAdmin")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateByAdmin(@RequestBody AdminFactorRequest factor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update_at = LocalDateTime.parse(formatDateTime, formatter);
        Factor factor1 = null;
        List<Cart> cartList = null;
        try {
            factor1 = factorService.findFactorById(factor.getId());
        } catch (Exception e) {
            e.getMessage();
        }
        if (factor1 != null) {
           Payment payment = factor1.getPayment();
           payment.setStatus(checkStatus(factor.getStatus()));
            factor1.setPayment(payment);
            factor1.setUpdate_at(update_at);
            try {
                factor1.setTrackingCode(factor.getTrackingCode());
                factor1.setPayWay(factor.getPayWay());
            }catch (Exception e){
                e.getMessage();
            }

                cartList = cartRepository.findCartsByFactorId(factor.getId());
                if (cartList != null) {
                    List<ProductCount> productCountList = new ArrayList<>();
                    for (Cart item : cartList) {
                        productCountList.add(item.getProductCount());
                    }
                    DiscountCalculation discountCalculation = new DiscountCalculation();
                    List<ProductCountResponse> productCountResponse = discountCalculation.calculation(productCountList);
                    for (Cart item : cartList) {
                        for (ProductCountResponse pcount : productCountResponse) {
                            if (item.getProductCount().getId()==pcount.getId()){
                                item.setFactorFinalPrice(item.getFactorFinalPrice());
                                item.setFactorMainPrice(item.getFactorMainPrice());
                                if (pcount.getDiscount()!=null){
                                    item.setFactorDiscount(item.getFactorDiscount());
                                    item.setFactorDiscountVal(item.getFactorDiscountVal());
                                }
                            }
                        }
                    }
                    cartRepository.saveAll(cartList);
                }
            Set<ShipmentStatus> finalShipmentStatus = new HashSet<>();
            if (factor.getShipmentStatus() > 0) {
                switch (factor.getShipmentStatus()) {
                    case 1 -> {
                        ShipmentStatus SHIPMENT_ACCEPT = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_ACCEPT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_ACCEPT);
                        sendSms(factor1.getUsers().getMobile(), 1);
                    }
                    case 2 -> {
                        ShipmentStatus SHIPMENT_FACTOR = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_FACTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_FACTOR);
                        sendSms(factor1.getUsers().getMobile(), 2);
                    }
                    case 3 -> {
                        ShipmentStatus SHIPMENT_PACKING = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_PACKING)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_PACKING);
                        sendSms(factor1.getUsers().getMobile(), 3);
                    }
                    case 4 -> {
                        ShipmentStatus SHIPMENT_SEND_TO_POST = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_SEND_TO_POST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_SEND_TO_POST);
                        sendSms(factor1.getUsers().getMobile(), 4);
                    }
                    case 5 -> {
                        ShipmentStatus SHIPMENT_SEND = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_SEND)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_SEND);
                        sendSms(factor1.getUsers().getMobile(), 5);
                    }
                    case 6 -> {
                        ShipmentStatus SHIPMENT_RECIEVE = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_RECIEVE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_RECIEVE);
                        sendSms(factor1.getUsers().getMobile(), 6);
                    }
                    case 7 -> {
                        ShipmentStatus SHIPMENT_CANCEL = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_CANCEL)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_CANCEL);
                        sendSms(factor1.getUsers().getMobile(), 7);
                    }
                    case 8 -> {
                        ShipmentStatus SHIPMENT_RETURN = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_RETURN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_RETURN);
                        sendSms(factor1.getUsers().getMobile(), 8);
                    }
                    case 9 -> {
                        ShipmentStatus SHIPMENT_ROLLBACK = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_ROLLBACK)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        finalShipmentStatus.add(SHIPMENT_ROLLBACK);
                        sendSms(factor1.getUsers().getMobile(), 9);
                    }
                }
            }
            factor1.setShipmentStatus(finalShipmentStatus);
        }
        factorService.addObject(factor1);
        return ResponseEntity.ok("factor is updated!");
    }



    @PostMapping("/getAllUserCart/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllUserCart(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(factorService.getAllUserCart(pageNumber));
    }



    @PostMapping("/findFactorById/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findFactorById(@PathVariable("id") long id) {
        return ResponseEntity.ok(factorService.findFactorById(id));
    }


    @PostMapping("/findFactorsByUsersMobileAndStatus")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findFactorsByUsersMobileAndstatus(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(factorService.findFactorsByUsersMobileAndstatus(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/filterFactorByMobile")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> filterFactorByMobile(@RequestBody ProductRequest productRequest) {
        if (!productRequest.getName().equals("")) {
            return ResponseEntity.ok(factorService.filterFactorByMobile(productRequest.getName()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/findFactorByTypeOfStatusforPagination/{status}/{shipment}/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> findFactorByTypeOfStatusforPagination(@PathVariable("status") String status,@PathVariable("shipment") int shipment,@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(factorService.findFactorByTypeOfStatusforPagination(status,shipment,pageNumber));
    }



    @PostMapping("/getAllShipmentStatus")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllShipmentStatus() {
        return ResponseEntity.ok(shipmentStatusRepository.findAll());
    }


    @PostMapping("/getAllCartsByStatus/{status}/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCartsByStatus(@PathVariable("status") Status status,@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(factorService.getAllCartsByStatus(status,pageNumber));
    }



    public void sendSms(String mobile, int number) {
        String description = "";
        switch (number) {
            case 1:
                description = "مشترک گرامی کالای مورد نظر شما ثبت شد با تشکر موبایل گاندی";
                break;
            case 2:
                description = "مشترک گرامی کالای انتخاب شده فاکتور شده است با تشکر موبایل گاندی";
                break;
            case 3:
                description = "مشترک گرامی کالای مورد نظر شما در حال بسته بندی میباشد با تشکر موبایل گاندی";
                break;
            case 4:
                description = "مشترک گرامی کالای مورد نظر شما به دفتر پست ارسال شده است با تشکر موبایل گاندی";
                break;
            case 5:
                description = "مشترک گرامی کالای مورد نظر شما توسط دفتر پست ارسال شده است با تشکر موبایل گاندی";
                break;
            case 6:
                description = "مشترک گرامی کالای مورد نظر شما تحویل داده شده است با تشکر موبایل گاندی";
                break;
            case 7:
                description = "مشترک گرامی درخواست خرید شما کنسل شد با تشکر موبایل گاندی";
                break;
            case 8:
                description = "مشترک گرامی درخواست شما برای برگشت هزینه و انصراف از خرید کالا ثبت شد  با تشکر موبایل گاندی";
                break;
            case 9:
                description = "مشترک گرامی درخواست شما برای خرید کالا به دلیل ناموجود بودن کالا لغو شد با تشکر موبایل گاندی";
                break;
        }
        final String uri = "https://panel.ghasedaksms.com/tools/urlservice/send/?username=farzad.arabs@gmail.com&password=Sindin46044604!&from=9999178836&to=" + mobile + "&message= " + description + " ";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
    }


    public Status checkStatus(String status){
        switch (status){
            case "SETTLED": {
                return Status.SETTLED;
            }
            case "CANCELED": {
                return Status.CANCELED;
            }
            case "ROLLBACK": {
                return Status.ROLLBACK;
            }
            case "RETURN": {
                return Status.RETURN;
            }
        }
        return Status.CANCELED;
    }


}
