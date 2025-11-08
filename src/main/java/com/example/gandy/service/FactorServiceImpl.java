package com.example.gandy.service;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.FactorRequest;
import com.example.gandy.payload.request.RequestProduct;
import com.example.gandy.repo.*;
import com.example.gandy.security.services.UserDetailsImpl;
import com.example.gandy.service.payment.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FactorServiceImpl {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    ShipmentStatusRepository shipmentStatusRepository;

    @Autowired
    CartRepository cartRepository;


    @Autowired
    FactorRepository factorRepository;

    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    PaymentServiceImpl paymentService;


    public Factor createObjects(FactorRequest objects) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime create_at = LocalDateTime.parse(formatDateTime, formatter);
        Factor factor = new Factor();
        Set<ShipmentStatus> finalShipmentStatus = new HashSet<>();
        factor.setStatus(objects.getStatus());
        ShipmentStatus SHIPMENT_CART = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_CART)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        finalShipmentStatus.add(SHIPMENT_CART);
        factor.setShipmentStatus(finalShipmentStatus);
        factor.setUsers(usersService.findObjects(objects.getUsers()));
        factor.setCreate_at(create_at);
        factor.setPayment(paymentService.addObject(objects.getPayment()));
        try {
            factor =factorRepository.save(factor);
        }catch (Exception e){
            e.getMessage();
        }
        return factor;
    }


    public void addObject(Factor object) {
        factorRepository.save(object);
    }


    public void deleteObjects(long id) {
        factorRepository.deleteById(id);
    }

    public void deleteCartAndFactor() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        factorRepository.deleteFactorByUsersMobileAndStatus(userDetails1.getMobile() , 0);
    }


    public Factor findFactorByUserAndId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        return factorRepository.findFactorByUsers(userDetails1.getId() , Status.CART);
    }

    public Factor findFactorByUsersAAndPaymentStatus(Status status) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        return factorRepository.findFactorByUsersAAndPaymentStatus(userDetails1.getId() , status);
    }


    public Page<Factor> getAllUserCart(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        List<Factor> factors = factorRepository.getAllUserCart(Status.CART , paging);

        for (Factor factor:factors) {
            List<Cart> cartList = cartRepository.findCartsByFactorId(factor.getId());
            if (cartList.size()<=0){

                factors = factors.stream()
                        .filter(select -> !select.equals(factor))
                        .collect(Collectors.toList());

            }
        }
        return new PageImpl<>(factors, paging, factors.size() - 1);
    }

    public List<Factor> findFactorsByUsersMobileAndstatus(String mobile) {
        Pageable paging = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "id"));
        return factorRepository.findFactorsByUsersMobileAndstatus(mobile,Status.CART,paging);
    }


    public List<Factor> filterFactorByMobile(String mobile) {
        Pageable paging = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "id"));
        return factorRepository.filterFactorByMobile(mobile ,Status.CART,paging);
    }


    public Page<Factor> findFactorByTypeOfStatusforPagination(String status,int shipment,int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        if (!status.equals("") && !status.equals("NOTHING")) {
            switch (status) {
                case "CART": {
                    return factorRepository.findFactorByTypeOfStatusforPagination(Status.CART, paging);
                }
                case "SETTLED": {
                    return factorRepository.getAllCartsByStatusforPagination(Status.SETTLED, paging);
                }
                case "CANCELED": {
                    return factorRepository.getAllCartsByStatusforPagination(Status.CANCELED, paging);
                }
                case "ROLLBACK": {
                    return factorRepository.getAllCartsByStatusforPagination(Status.ROLLBACK, paging);
                }
                case "RETURN": {
                    return factorRepository.getAllCartsByStatusforPagination(Status.RETURN, paging);
                }
            }
        }else if(shipment!=0){
            List<Factor> factorList = factorRepository.getAllFactors(paging);
            List<Factor> result = new ArrayList<>();

            switch (shipment) {
                case 1: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==1){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }
                case 2: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==2){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }
                case 3: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==3){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }
                case 4: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==4){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }
                case 5: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==5){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }

                case 6: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==6){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }

                case 7: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==7){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }

                case 8: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==8){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }


                case 9: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==9){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }

                case 10: {
                    for (Factor factor : factorList) {
                        if(factor.getShipmentStatus().size()>0 && factor.getShipmentStatus().stream().findAny().get().getId()==10){
                            result.add(factor);
                        }
                    }
                    return new PageImpl<>(result, paging, factorList.size() - 1);
                }

            }
        }
        return factorRepository.findFactorByTypeOfStatusforPagination(Status.CART , paging);
    }



    public List<Factor> getAllCartsByStatus(Status status , int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return factorRepository.getAllCartsByStatus(status,paging);
    }



    public Factor findByfactorIdAndUserId(long id , long userId) {
        return factorRepository.findByfactorIdAndUserId(id , userId);
    }



    public Factor findFactorById(long id) {
            return factorRepository.findFactorById(id);
    }

    public Factor findFactorBypaymentAuthority(String authority) {
        return factorRepository.findFactorBypaymentAuthority(authority);
    }

    public Factor findFactorByUserId(long id) {
        return factorRepository.findFactorByUsers(id , Status.CART);
    }


}
