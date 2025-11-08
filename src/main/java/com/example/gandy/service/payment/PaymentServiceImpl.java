package com.example.gandy.service.payment;

import com.example.gandy.entity.Payment;
import com.example.gandy.repo.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class PaymentServiceImpl {
    @Autowired
    private PaymentRepository repository;


    public Payment addObject(Payment object) {
        return repository.save(object);
    }

    public void updateObject(Payment object) {
        repository.save(object);
    }

    public Payment findByAuthority(String authority) {
        return repository.findPaymentByAuthority(authority);
    }

    public Payment findPaymentById(long id) {
        return repository.findPaymentById(id);
    }




    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
