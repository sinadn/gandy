package com.example.gandy.repo;

import com.example.gandy.entity.Cart;
import com.example.gandy.entity.Factor;
import com.example.gandy.entity.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FactorRepository extends JpaRepository<Factor, Long> {

    @Query("SELECT f FROM Factor f WHERE f.id=?1 and f.users.id=?2")
    Factor findByfactorIdAndUserId(long factorId , long usersId);

    Factor findFactorById(long id);

    @Query("SELECT f FROM Factor f WHERE f.payment.status=?1")
    List<Factor> getAllUserCart(Status status,Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.payment.status=?1")
    List<Factor> getAllCartsByStatus(Status status , Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.users.mobile like %:mobile% and f.payment.status=:status")
    List<Factor> findFactorsByUsersMobileAndstatus(@Param("mobile") String mobile ,@Param("status") Status status , Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.users.mobile like %:mobile% and f.payment.status<>:status")
    List<Factor> filterFactorByMobile(@Param("mobile") String mobile ,@Param("status") Status status , Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.status=2")
    Page<Factor> findAcceptedPaymentFactors(Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.status=1")
    Page<Factor> findUndecidedFactors(Pageable pageable);


    @Query("SELECT f FROM Factor f WHERE f.users.mobile like %:mobile% and f.status=1")
    List<Factor> findFactorsByMobileUndecided(@Param("mobile") String mobile , Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.payment.status<>?1")
    Page<Factor> findFactorByTypeOfStatusforPagination(Status status, Pageable pageable);

    @Query("SELECT f FROM Factor f WHERE f.payment.status=?1")
    Page<Factor> getAllCartsByStatusforPagination(Status status , Pageable pageable);

    @Query("SELECT f FROM Factor f")
    List<Factor> getAllFactors (Pageable pageable);


    @Query("SELECT f FROM Factor f WHERE f.users.id=?1 and f.payment.status<>?2")
    List<Factor> findFactorByUserAndStatus(long user , Status status);


    @Query("SELECT f FROM Factor f WHERE f.payment.authority=?1")
    Factor findFactorBypaymentAuthority(String authority);


    @Query("SELECT f FROM Factor f WHERE f.users.id=?1 and f.payment.status=?2")
    Factor findFactorByUsers(long id , Status status);

    @Query("SELECT f FROM Factor f WHERE f.users.id=?1 and f.payment.status=?2")
    Factor findFactorByUsersAAndPaymentStatus(long id , Status status);


    @Query("SELECT f FROM Factor f WHERE f.users.id=?1 and f.id=?2")
    Factor factorConvertToPdf(long userId , long factorId);



    @Modifying
    @Query("delete from Factor f WHERE f.users.mobile=?1 and f.status=0")
    void deleteCartAndFactor(String mobile);

    void deleteFactorByUsersMobileAndStatus(String mobile , int status);
}