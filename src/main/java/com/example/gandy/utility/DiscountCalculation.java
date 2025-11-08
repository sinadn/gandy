package com.example.gandy.utility;

import com.example.gandy.entity.FavoriteProduct;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.payload.response.ProductCountResponse;
import com.example.gandy.repo.ProductCountRepository;
import com.example.gandy.service.ProductCountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiscountCalculation {
    ProductCountServiceImpl productCountService;
    public  DiscountCalculation(ProductCountServiceImpl productCountService){
        this.productCountService=productCountService;
    }


    public  DiscountCalculation(){}



    public List<ProductCountResponse> calculation(List<ProductCount> productCountList) {
        List<ProductCountResponse> productCountResponseArrayList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();

        float v = 0;
        long result = 0;
        try {
            for (int i = 0; i < productCountList.size(); i++) {
                ProductCountResponse productAccessResponse = new ProductCountResponse();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formatDateTime = LocalDateTime.now().format(formatter);
                LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
                try {
                    colorList = productCountService.findProductColor(productCountList.get(i).getProduct().getId());
                    productAccessResponse.setColorList(colorList);
                }catch (Exception e){
                    e.getMessage();
                }


                if (productCountList.get(i).getDiscount() != null && productCountList.get(i).getDiscount().getDiscount() != null
                && today.isAfter(productCountList.get(i).getDiscount().getCreate_at()) && today.isBefore(productCountList.get(i).getDiscount().getExpire_at())
                ) {
                    v = ((Integer.parseInt(productCountList.get(i).getDiscount().getDiscount()) / 100.0f * productCountList.get(i).getPrice()));
                    result = (long) (productCountList.get(i).getPrice() - v);
                    productAccessResponse.setDiscountVal((long) v);
                    productAccessResponse.setFinalPrice(result);
                    productAccessResponse.setDiscount(productCountList.get(i).getDiscount());
                } else {
                    productAccessResponse.setFinalPrice(productCountList.get(i).getPrice());
                }
                productAccessResponse.setProduct(productCountList.get(i).getProduct());
                productAccessResponse.setColor(productCountList.get(i).getColor());
                productAccessResponse.setCount(productCountList.get(i).getCount());
                productAccessResponse.setColorHex(productCountList.get(i).getColorHex());
                productAccessResponse.setMain(productCountList.get(i).getMain());
                productAccessResponse.setPrice(productCountList.get(i).getPrice());
                productAccessResponse.setId(productCountList.get(i).getId());
                productAccessResponse.setProductImage(productCountList.get(i).getProductImage());
                productCountResponseArrayList.add(productAccessResponse);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return productCountResponseArrayList;
    }


    public List<ProductCountResponse> calculationForFavoriteProduct(List<FavoriteProduct> favoriteProductList) {
        List<ProductCountResponse> productCountResponseArrayList = new ArrayList<>();
        float v = 0;
        long result = 0;
        try {
            for (int i = 0; i < favoriteProductList.size(); i++) {
                ProductCountResponse productAccessResponse = new ProductCountResponse();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formatDateTime = LocalDateTime.now().format(formatter);
                LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);

                if (favoriteProductList.get(i).getProductCount().getDiscount() != null && favoriteProductList.get(i).getProductCount().getDiscount().getDiscount() != null
                        && today.isAfter(favoriteProductList.get(i).getProductCount().getDiscount().getCreate_at()) && today.isBefore(favoriteProductList.get(i).getProductCount().getDiscount().getExpire_at())
                ) {
                    v = ((Integer.parseInt(favoriteProductList.get(i).getProductCount().getDiscount().getDiscount()) / 100.0f * favoriteProductList.get(i).getProductCount().getPrice()));
                    result = (long) (favoriteProductList.get(i).getProductCount().getPrice() - v);
                    productAccessResponse.setDiscountVal((long) v);
                    productAccessResponse.setFinalPrice(result);
                    productAccessResponse.setDiscount(favoriteProductList.get(i).getProductCount().getDiscount());
                } else {
                    productAccessResponse.setFinalPrice(favoriteProductList.get(i).getProductCount().getPrice());
                }
                productAccessResponse.setProduct(favoriteProductList.get(i).getProductCount().getProduct());
                productAccessResponse.setColor(favoriteProductList.get(i).getProductCount().getColor());
                productAccessResponse.setCount(favoriteProductList.get(i).getProductCount().getCount());
                productAccessResponse.setColorHex(favoriteProductList.get(i).getProductCount().getColorHex());
                productAccessResponse.setMain(favoriteProductList.get(i).getProductCount().getMain());
                productAccessResponse.setPrice(favoriteProductList.get(i).getProductCount().getPrice());
                productAccessResponse.setId(favoriteProductList.get(i).getProductCount().getId());
                productAccessResponse.setFp_id(favoriteProductList.get(i).getId());
                productAccessResponse.setProductImage(favoriteProductList.get(i).getProductCount().getProductImage());
                productCountResponseArrayList.add(productAccessResponse);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return productCountResponseArrayList;
    }
}
