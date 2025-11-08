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
public class Warranty {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private RegWarranty regWarranty ;
    @ManyToOne
    private Product product ;


    @OneToMany(mappedBy = "warranty" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Cart> cartList = new HashSet<>();

    @PreRemove
    public void deleteObjects(){
        if (cartList != null)
            this.cartList.forEach(cart -> cart.setWarranty(null));
    }
}
