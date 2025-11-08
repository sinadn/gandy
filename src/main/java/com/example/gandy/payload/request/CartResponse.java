package com.example.gandy.payload.request;

import com.example.gandy.payload.response.ProductCountBox;
import com.example.gandy.payload.response.ProductCountResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponse {
        private List<ProductCountBox> list;
        private int finalPrice;
        private int totalDiscount;
        private int totalMainPrice;
        private int shipment;

}
