package com.example.gandy.service;

import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.repo.PimagesRepository;
import com.example.gandy.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PimagesServiceImpl {
    @Autowired
    PimagesRepository pimagesRepository;



    public void addObject(ProductImage objects) {
        try {
            pimagesRepository.save(objects);
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public ProductImage getByIdObject(long id) {
      return  pimagesRepository.findProductById(id);
    }



    public List<ProductImage> getAllObjects() {
        return pimagesRepository.findAll();
    }



    public void deleteByIdObject(long id) {
        pimagesRepository.deleteById(id);
    }


}
