package com.example.gandy.payload.request;


import com.example.gandy.entity.ProductCount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountRequest {
    private long id;
    private String  discount;
    private String create_at;
    private String expire_at;
    


}
