package com.example.gandy.repo;

import com.example.gandy.entity.Activation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivationRepository extends JpaRepository<Activation, Long> {
    @Query("SELECT a FROM Activation a WHERE  a.users.mobile=?1 and a.otp=?2 order by a.id desc ")
    Activation confirmOtp(String mobile, String otp);

}