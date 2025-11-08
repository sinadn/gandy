package com.example.gandy.repo;

import com.example.gandy.entity.ProductSeen;
import com.example.gandy.entity.TagAmountBox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSeenRepository extends JpaRepository<ProductSeen, Long> {

    ProductSeen getProductSeenById(long id);

    List<ProductSeen> getProductSeenByProductCountId(long id , Pageable pageable);

    @Query("SELECT p FROM ProductSeen p WHERE p.users.id =?1  order by p.id desc ")
    List<ProductSeen> getProductSeenByUsersId(long id , Pageable pageable);

    ProductSeen getProductSeenByUsersIdAndProductCountId(long userId , long productCountId);

    Integer countProductSeenByProductCountId(long id);

}