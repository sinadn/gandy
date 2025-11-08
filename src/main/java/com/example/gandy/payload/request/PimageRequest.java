package com.example.gandy.payload.request;

import com.example.gandy.entity.Brand;
import com.example.gandy.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PimageRequest {
    public String img;
    public long product;
}
