package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class TagAmountBox {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private int box;
    @Nullable
    private String name;
    @Nullable
    private long amount;
    private int num;
    private String image;
    @Nullable
    @ManyToOne
    private ProductType productType;
    @Nullable
    @ManyToOne
    private Tag productTag;
}
