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
public class ProductBox {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    public int boxNum;
    @ManyToOne
    private ProductCount productCount;
}
