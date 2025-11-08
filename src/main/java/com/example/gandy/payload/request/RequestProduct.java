package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProduct {
    private long productCountId;
    private int count;
    private long cartId;
    private int warranty;
}
