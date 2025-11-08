package com.example.gandy.payload.response;

import com.example.gandy.entity.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class ProductCountResponse {
    private long id;
    private long price;
    private long fp_id;
    private String color;
    private String colorHex;
    private int count;
    private int main;
    private Product product;
    private ProductImage productImage;
    private Discount discount ;
    private long finalPrice;
    private long discountVal;
    private List<String> colorList;

}
