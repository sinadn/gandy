package com.example.gandy.payload.response;

import com.example.gandy.entity.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse {
    private long id;
    private String name;
    private String url;
    @Nullable
    private String image;
    private ProductType productType;
    @Nullable
    private Tag productTag;
    @Nullable
    private AttributeOption attributeOption;
    @Nullable
    private long amount;
    @Nullable
    private Integer order_val;
    @Nullable
    private List<MenuResponse> subMenuResponses;
}
