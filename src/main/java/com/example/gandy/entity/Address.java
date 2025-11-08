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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postalCode;
    private String address;
    private String no;
    private String unit;
    private String area;

    @ManyToOne
    private Province province;
    @ManyToOne
    private City city;
    @ManyToOne
    private Users users;

    //    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
//    @OneToMany(mappedBy = "address" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
//    @com.fasterxml.jackson.annotation.JsonIgnore
//    private Set<Factor> factorList = new HashSet<>();

//    @PreRemove
//    public void deleteObjects() {
//        if (factorList != null)
//            this.factorList.forEach(factor -> factor.setAddress(null));
//    }
}
