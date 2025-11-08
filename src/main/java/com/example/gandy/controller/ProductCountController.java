package com.example.gandy.controller;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.Warranty;
import com.example.gandy.exception.TokenRefreshException;
import com.example.gandy.payload.request.ProductRequest;
import com.example.gandy.payload.request.SearchProductRequest;
import com.example.gandy.payload.response.AmazingOfferResponse;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.uploadconfig.FileStorageService;
import com.example.gandy.utility.DiscountCalculation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/pcount")
public class ProductCountController {

    @Autowired
    ProductCountRepository repository;

    @Autowired
    ProductCountServiceImpl productCountService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addProductCount(@RequestBody ProductCount productCount) {
        productCountService.createObjects(productCount);
        return ResponseEntity.ok("ProductCount register successfully!");
    }





    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody ProductCount productCount) {
        repository.findById(productCount.getId())
                .map(item -> {
                    item.setProduct(productCount.getProduct());
                    item.setDiscount(productCount.getDiscount());
                    item.setProductImage(productCount.getProductImage());
                    item.setCount(productCount.getCount());
                    item.setColor(productCount.getColor());
                    item.setColorHex(productCount.getColorHex());
                    item.setMain(productCount.getMain());
                    item.setPrice(productCount.getPrice());
                    productCountService.createObjects(item);
                    return ResponseEntity.ok("Product updated successfully!");
                }).orElseGet(() -> {
                    productCountService.createObjects(productCount);
                    return ResponseEntity.ok("Product register successfully!");
                });
        return ResponseEntity.ok("Product registered successfully!");
    }


    @PostMapping("/getAllColor/{id}")
    public ResponseEntity<?> getAllColor(@PathVariable("id") long id) {
        return ResponseEntity.ok(productCountService.findProductColor(id));
    }


    @PostMapping("/getByProductId/{id}")
    public ResponseEntity<?> findProduct(@PathVariable("id") long id) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findProduct(id)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/findCorePcountsByProductId/{id}")
    public ResponseEntity<?> findCorePcountsByProductId(@PathVariable("id") long id) {
        return ResponseEntity.ok(productCountService.findProduct(id));
    }


    @PostMapping("/getByProductName/{name}")
    public ResponseEntity<?> findProduct(@PathVariable("name") String name) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findByProductName(name)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/findProductCount/{id}")
    public ResponseEntity<?> findProductCount(@PathVariable("id") long id) {
        return ResponseEntity.ok(productCountService.findProductCount(id));
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> delMenu(@RequestBody ProductRequest productRequest) {
        productCountService.deleteObjects(productRequest.getId());
        return ResponseEntity.ok("productCount removed successfully!");
    }


    @PostMapping("/getByBrand/{id}/{pageNumber}")
    public ResponseEntity<?> getByBrand(@PathVariable("id") int id, @PathVariable("pageNumber") int pageNumber) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findProductByBrand_Id(id, pageNumber)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/getByProductType/{ptype}/{pageNumber}")
    public ResponseEntity<?> getByProductType(@PathVariable("ptype") int ptype, @PathVariable("pageNumber") int pageNumber) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.getByProductType(ptype, pageNumber)));
        return ResponseEntity.ok(amazingOfferResponse);
    }

    @PostMapping("/getByProductType/{ptId}/brand/{brandId}")
    public ResponseEntity<?> findProductByProductTypeAndBrand(@PathVariable("ptId") int ptId, @PathVariable("brandId") int brandId) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findProductByProductTypeAndBrand(ptId, brandId)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/findAmazingOffer/{pageNumber}")
    public ResponseEntity<?> findAmazingOffer(@PathVariable("pageNumber") int pageNumber) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findAmazingOffer(pageNumber)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/getProductByWords")
    public ResponseEntity<?> getProductByWords(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
            DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
            amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findProductByWords(productRequest.getName())));
            return ResponseEntity.ok(amazingOfferResponse);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @PostMapping("/findProductCountByWordsForAdmin")
    public ResponseEntity<?> findProductCountByWordsForAdmin(@RequestBody ProductRequest productRequest) {
        if (!productRequest.name.equals("")) {
            AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
            DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
            amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findProductCountByWordsForAdmin(productRequest.getName())));
            return ResponseEntity.ok(amazingOfferResponse);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }


    @GetMapping("/searchProduct")
    public ResponseEntity<?> getProductByWords(@RequestParam List<Integer> ato, @RequestParam long amount, @RequestParam int ptype
            , @RequestParam int Tag, @RequestParam int sortByPrice, @RequestParam int sortByNewerProduct,
                                               @RequestParam int sortByMostDiscount, @RequestParam int sortByAvailableProduct
            , @RequestParam int amazingOffer , @RequestParam int page) {
        SearchProductRequest object = new SearchProductRequest();
        object.setProductType(ptype);
        object.setAmount(amount);
        object.setAttributeOption(ato);
        object.setTag(Tag);
        object.setAmazingOffer(amazingOffer);
        object.setSortByNewerProduct(sortByNewerProduct);
        object.setSortByAvailableProduct(sortByAvailableProduct);
        object.setSortByMostDiscount(sortByMostDiscount);
        object.setSortByPrice(sortByPrice);

        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
            amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.advanceSearch(object,page)));
        return ResponseEntity.ok(amazingOfferResponse);
    }


    @PostMapping("/getAllProductCount/{pageNumber}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllProductCount(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(productCountService.getAllProductCount(pageNumber));
    }


    @PostMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(productCountService.findAll(pageNumber));
    }


    @PostMapping("/getNewProduct/{pageNumber}")
    public ResponseEntity<?> findNewProduct(@PathVariable("pageNumber") int pageNumber) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findNewProduct(pageNumber)));
        return ResponseEntity.ok(amazingOfferResponse);
    }

    @PostMapping("/findByprice/{pageNumber}")
    public ResponseEntity<?> findByprice(@RequestBody ProductRequest productRequest, @PathVariable("pageNumber") int pageNumber) {
        AmazingOfferResponse amazingOfferResponse = new AmazingOfferResponse();
        DiscountCalculation discountCalculation = new DiscountCalculation(productCountService);
        amazingOfferResponse.setProductCountResponses(discountCalculation.calculation(productCountService.findByprice(productRequest.getPrice(), pageNumber)));
        return ResponseEntity.ok(amazingOfferResponse);
    }
}
