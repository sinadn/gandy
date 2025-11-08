package com.example.gandy.payload.response;

import com.example.gandy.entity.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FactorResponse {
        private List<Cart> cartList;
        private int finalPrice;
        private int totalDiscount;
        private int totalMainPrice;
        private String shipmentType;
        private int shipment;
        private int result;
}
