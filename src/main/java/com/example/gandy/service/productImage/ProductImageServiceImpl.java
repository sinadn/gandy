package com.example.gandy.service.productImage;

import com.example.gandy.entity.*;
import com.example.gandy.repo.ProductImageRepository;
import com.example.gandy.repo.WebInfoRepository;
import com.example.gandy.uploadconfig.FileStorageService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class ProductImageServiceImpl implements ProductImageService<ProductImage> {
    @Autowired
    private ProductImageRepository repository;

    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public void addObject(ProductImage object) {
        repository.save(object);
    }

    public ResponseEntity<?> uploadimg(long id, MultipartFile[] files) {
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).forEach(file -> {
                fileStorageService.storeFile(file);
                fileNames.add(file.getOriginalFilename());
            });
            if (fileNames.size() != 0) {
                fileNames.forEach(filename -> {
                    ProductImage response = new ProductImage();
                    Product product = new Product();
                    product.setId(id);
                    response.setProduct(product);
                    response.setImg(filename);
                    repository.save(response);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(false);
        }
    }



    @Override
    public void updateObject(ProductImage object) {
        repository.save(object);
    }


    @Override
    public Optional<ProductImage> getByIdObject(Long id) {
        return repository.findById(id);
    }


    public ProductImage getById(Long id) {
        return repository.findProductImageById(id);
    }


    public List<ProductImage> getProductImageByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return repository.getProductImageByWords(name, paging);
    }


    @Override
    public List<ProductImage> findByProductId(Long id) {
        return repository.findByProductId(id);
    }

    @Override
    public Collection<ProductImage> getAllObjects() {
        return repository.findAll();
    }

    public Page<ProductImage> getAllProductImage(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }

    @Override
    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }

    public void deleteProductImageByImg(String name) {
        repository.deleteProductImageByImg(name);
    }

    public void deleteImageField(long id) {
        ProductImage productImage = getById(id);
        productImage.setImg("");
        repository.save(productImage);
    }


}

