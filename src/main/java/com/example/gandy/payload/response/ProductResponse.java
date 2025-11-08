package com.example.gandy.payload.response;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import com.example.gandy.entity.ProductInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class ProductResponse {
    private Product product;
    private ProductInfo productInfo;
}
