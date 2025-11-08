package com.example.gandy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String image;
    private int price;
//    @OneToMany(mappedBy = "shipment" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
//    @com.fasterxml.jackson.annotation.JsonIgnore
//    private Set<Factor> factorList = new HashSet<>();

//    @PreRemove
//    public void deleteObjects(){
//        if (factorList != null)
//            this.factorList.forEach(factor -> factor.setShipment(null));
//    }

}
