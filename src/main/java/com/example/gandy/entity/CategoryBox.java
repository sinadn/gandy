package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryBox {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;
    public String name;
    public String img;
    public Integer num;
    public Boolean main;
    @ManyToOne
    @Nullable
    private ProductType productType;
}
