package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Slider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Nullable
    private Integer orderVal;
    private String url;

    @Nullable
    private long amount;
    @Nullable
    @ManyToOne
    private ProductType productType;

    @Nullable
    @ManyToOne
    private Tag productTag;

    @Nullable
    @ManyToOne
    private AttributeOption attributeOption;
}
