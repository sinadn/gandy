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
public class Brand {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    private String image;


    @OneToMany(mappedBy = "brand" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Product> products = new HashSet<>();

    @PreRemove
    public void deleteObjects(){
        if (products != null)
            this.products.forEach(product -> product.setBrand(null));
    }


}
