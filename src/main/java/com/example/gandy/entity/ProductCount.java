package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long price;
    private String color;
    private String colorHex;
    private int count;
    @Nullable
    @ManyToOne
    private Discount discount;
    @Nullable
    @ManyToOne
    private Product product;
    @Nullable
    @ManyToOne
    private ProductImage productImage;
    private int main;


    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductSuggestion> productSuggestions = new HashSet<>();

    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<FavoriteProduct> favoriteProducts = new HashSet<>();

    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Cart> cartList = new HashSet<>();

    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductBox> productBoxes = new HashSet<>();

    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductSeen> productSeens = new HashSet<>();



}
