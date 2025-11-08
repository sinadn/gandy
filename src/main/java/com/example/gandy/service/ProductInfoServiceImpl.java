package com.example.gandy.service;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.repo.AddressRepository;
import com.example.gandy.repo.ProductInfoRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProductInfoServiceImpl {
    @Autowired
    ProductInfoRepository productInfoRepository;


    public void addObject(ProductInfo object) {
        productInfoRepository.save(object);
    }

    public void updateObject(ProductInfo object) {
        try {
            ProductInfo productInfo = productInfoRepository.findProductInfoByProductId(object.getProduct().getId());
            if (productInfo!=null){
                productInfo.setProduct(object.getProduct());
                productInfo.setAttributes(object.getAttributes());
                productInfoRepository.save(productInfo);
            }else {
                productInfo = new ProductInfo();
                productInfo.setProduct(object.getProduct());
                productInfo.setAttributes(object.getAttributes());
                productInfoRepository.save(productInfo);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }


    public ProductInfo findProductInfoById(long id) {
       return productInfoRepository.findProductInfoById(id);
    }

    public ProductInfo findProductInfoByProductId(long id) {
        return productInfoRepository.findProductInfoByProductId(id);
    }


    public void deleteProductInfo(long id) {
         productInfoRepository.deleteProductInfoByProductId(id);
    }

}
