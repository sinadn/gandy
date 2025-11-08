package com.example.gandy.payload.request;


import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductCount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSugRequest {
    private long id;
    private Product product;
    private String create_at;
    private String expire_at;
    


}
