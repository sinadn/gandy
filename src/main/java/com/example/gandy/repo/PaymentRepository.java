package com.example.gandy.repo;

import com.example.gandy.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findPaymentByAuthority(String authority);

    Payment findPaymentById(long id);

}