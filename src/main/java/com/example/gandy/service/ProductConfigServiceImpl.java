package com.example.gandy.service;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductConfig;
import com.example.gandy.payload.request.ProductConfigRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.ProductConfigRepository;
import com.example.gandy.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductConfigServiceImpl {
    @Autowired
    ProductConfigRepository productConfigRepository;


    public void createObjects(ProductConfig objects) {
        productConfigRepository.save(objects);
    }

    public ProductConfig findProductConfigById(long id) {
      return  productConfigRepository.findProductConfigById(id);
    }

    public Page<ProductConfig> getAllProductConfig(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productConfigRepository.findAll(paging);
    }


    public void deleteByIdObject(Long id) {
        productConfigRepository.deleteById(id);
    }

    public List<ProductConfig> searchPConfigByPName(String name) {
        Pageable paging = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        return  productConfigRepository.searchPConfigByPName(name , paging);

    }


    public List<ProductConfig> searchPConfigByAO(String name) {
        Pageable paging = PageRequest.of(0, 40, Sort.by(Sort.Direction.DESC, "id"));
        return  productConfigRepository.searchPConfigByAO(name , paging);
    }






    public List<ProductConfig> findProductConfigByProduct(long id) {
        return  productConfigRepository.findProductConfigByProduct(id);
    }


}
