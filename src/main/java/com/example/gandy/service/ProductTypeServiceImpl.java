package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.ProductType;
import com.example.gandy.entity.Users;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductTypeServiceImpl {
    @Autowired
    ProductTypeRepository productTypeRepository;


    public List<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    public ProductType findObject(long id) {
        return productTypeRepository.findProductTypeBy(id);
    }


    public ProductType getProductTypeByName(String name) {
        return productTypeRepository.getProductTypeByName(name);
    }


    public List<Long> findPTfromhead(long id) {
        List<ProductType> result = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        result.add(findObject(id));
        for (int i = 0; i < result.size(); i++) {
            ProductType element = result.get(i);
            List<ProductType> temp = new ArrayList<>();
            temp = findParentPtype(element.getId());
            result.addAll(temp);
        }
        for (int i = 0; i < result.size(); i++) {
            numbers.add(result.get(i).getId());
        }
        System.out.println(numbers);
        return numbers;
    }


    public List<Long> findPTfromRoot(long id) {
        List<Long> numbers = new ArrayList<>();
        ProductType productType = findObject(id);
        numbers.add(productType.getId());
        boolean b = false;

        while (!b) {
            try {
                productType = productType.getParentProductType();
            } catch (Exception e) {
                e.getMessage();
            }
            if (productType != null) {
                numbers.add(productType.getId());
            } else {
                b = true;
            }
        }

        return numbers;
    }


    public List<ProductType> findParentPtype(long id) {
        return productTypeRepository.findParentPtype(id);
    }


    public Page<ProductType> getAllProductType(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return productTypeRepository.findAll(paging);
    }


    public List<ProductType> getProductTypeByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return productTypeRepository.getProductTypeByWords(name, paging);
    }

    public void deleteObjects(long id) {
        productTypeRepository.deleteById(id);
    }

    public ProductType createObjects(ProductType productType) {
        return productTypeRepository.save(productType);
    }


}
