package com.example.gandy.payload.request;


import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchProductRequest {
    public long amount;
    public int productType;
    public List<Integer> attributeOption;
    public int Tag;
    public int sortByPrice;
    public int sortByNewerProduct;
    public int sortByMostDiscount;
    public int sortByAvailableProduct;
    public int amazingOffer;



}




