package com.example.gandy.repo;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.ProductInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

    ProductInfo findProductInfoById(long id);

    ProductInfo findProductInfoByProductId(long id);

    void deleteProductInfoByProductId(long id);
}