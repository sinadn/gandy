package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cover {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int position;
    private int col;
    private String url;
    private String image;
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
