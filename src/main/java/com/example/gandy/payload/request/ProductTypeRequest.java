package com.example.gandy.payload.request;


import com.example.gandy.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTypeRequest {
    public int id;
    public String name;
    public String imgName;
    public ProductType productType;

}
