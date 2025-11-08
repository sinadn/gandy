package com.example.gandy.payload.request;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
public class ProductConfigRequest {
    public Long id;
    private AttributeOption attributeOption;
    private Product product;



}
