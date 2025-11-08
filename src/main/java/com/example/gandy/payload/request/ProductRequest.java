package com.example.gandy.payload.request;

import com.example.gandy.entity.Brand;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductRequest {

    public long id;
    public int AmazingOffer;
    public int price;
    public String description;
    public String name;
    public String intro;
    public String attributes;
    public ProductType productType;
    public Brand brand;
//    public String create_at;
    public List<ProductCount> pCountList;


}
