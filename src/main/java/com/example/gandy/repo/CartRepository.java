package com.example.gandy.repo;

import com.example.gandy.entity.Cart;
import com.example.gandy.entity.City;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findCartByIdAndFactorUsersMobile(long id,String mobile );

    List<Cart> findCartsByFactorId(long id);

    @Query("SELECT c FROM Cart c WHERE c.factor.payment.authority=?1")
    List<Cart> findCartsByPaymentAuthority(String authority);


    @Query("SELECT c FROM Cart c WHERE c.factor.users.mobile=?1 and c.factor.payment.status=?2")
    List<Cart> findByfactor(String mobile , Status status);

    @Query("SELECT c FROM Cart c WHERE c.factor.users.mobile=?1 and c.productCount.id=?2 and c.factor.payment.status=?3 ")
    Cart findObjectByFactor(String mobile, long productCountId,Status status);

    List<Cart> deleteCartByFactor_Users_Mobile(String mobile);



}