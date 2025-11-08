package com.example.gandy.payload.request;


import com.example.gandy.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubCategoryRequest {
    public long parentId;
    public long subId;
}
