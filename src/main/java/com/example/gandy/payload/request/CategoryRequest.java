package com.example.gandy.payload.request;


import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.ProductTag;
import com.example.gandy.entity.ProductType;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.security.Timestamp;

@Setter
@Getter
public class CategoryRequest {
    public int id;
    public String name;
    public int isMain;
    @Nullable
    public String imgName;
    public int isActive;
    public String url;
    @Nullable
    public int amount;
    public ProductType productType;
    @Nullable
    public AttributeOption attributeOption;
    @Nullable
    public ProductTag productTag;


}
