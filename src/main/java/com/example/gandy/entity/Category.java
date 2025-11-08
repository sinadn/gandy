package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private int isMain;
    @Nullable
    private String imgName;
    @NonNull
    private int isActive;
    @NonNull
    private String url;
    @Nullable
    private long amount;
    @Nullable
    @NonNull
    private Integer orderVal;
    @Nullable
    @ManyToOne
    private ProductType productType;

    @Nullable
    @ManyToOne
    private Tag productTag;

    @Nullable
    @ManyToOne
    private AttributeOption attributeOption;

    @OneToMany(mappedBy = "subId", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<SubCategory> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<SubCategory> subCategoryList = new HashSet<>();



}
