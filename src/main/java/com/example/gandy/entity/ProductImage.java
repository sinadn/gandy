package com.example.gandy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    private String img;

    @OneToMany(mappedBy = "productImage" , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductCount> productCounts = new HashSet<>();

    @PreRemove
    public void deleteObjects(){
        if (productCounts != null)
            this.productCounts.forEach(productCount -> productCount.setProductImage(null));
    }


}
