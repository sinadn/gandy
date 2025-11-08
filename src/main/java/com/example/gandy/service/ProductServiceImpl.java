package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.ProductRepository;
import com.example.gandy.service.productImage.ProductImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl {
    @Autowired
    ProductRepository productRepository;



    public Product createObjects(ProductRequest objects) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
        Product product = new Product();
        if (objects.getId()!=0){
            product.setId(objects.getId());
        }
        product.setDescription(objects.getDescription());
        product.setName(objects.getName());
        product.setProductType(objects.getProductType());
        product.setBrand(objects.getBrand());
        product.setAmazingOffer(objects.getAmazingOffer());
        product.setIntro(objects.getIntro());
        product.setCreate_at(today);
         return productRepository.save(product);
    }



    public Product updateObjects(ProductRequest objects) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
        Product productTbl = productRepository.findProductById(objects.getId());

        productTbl.setId(objects.getId());
        productTbl.setDescription(objects.getDescription());
        productTbl.setName(objects.getName());
        productTbl.setProductType(objects.getProductType());
        productTbl.setBrand(objects.getBrand());
        productTbl.setAmazingOffer(objects.getAmazingOffer());
        productTbl.setIntro(objects.getIntro());
        productTbl.setCreate_at(today);
        return productRepository.save(productTbl);
    }


    public Page<Product> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productRepository.getAll(paging);
    }


    public List<Product> findProductByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  productRepository.findProductByWords(name , paging);
    }


    public Optional<Product> getByIdObject(Long id) {
        return productRepository.findById(id);
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Product getById(Long id) {
        return productRepository.findProductById(id);
    }


    public void deleteObjects(long id) {
        productRepository.deleteById(id);
    }






//    public List<Object> findProductByBrand_Id(int id) {
//      return  productRepository.findProductByBrand_Id(id);
//    }
//
//    public List<Product> findProductByProductType(int id) {
//        return  productRepository.findProductByProductType(id);
//    }
//
//
//    public List<Object> findProductByWords(String name) {
//        return  productRepository.findProductByWords(name);
//    }
//
//
//
//    public List<Object> findProduct(long id) {
//        return productRepository.findProductById(id);
//    }



}
