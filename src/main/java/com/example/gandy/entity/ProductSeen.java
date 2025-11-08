package com.example.gandy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductSeen {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long id;
    @ManyToOne
    private Users users;
    @ManyToOne
    private ProductCount productCount;
}
