package com.example.gandy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegWarranty {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String companyName;
    private String image;
    private int price;

    @OneToMany(mappedBy = "regWarranty", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Warranty> warranties = new HashSet<>();
}
