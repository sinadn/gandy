package com.example.gandy.payload.response;


import com.example.gandy.entity.Warranty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductCountBox {
    private ProductCountResponse productCountResponse;
    private int count;
    private long cartId;
    private Warranty warranty;


}
