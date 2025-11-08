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
public class Province {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String tel_prefix;
    @OneToMany(mappedBy = "province" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Address> addressList = new HashSet<>();
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<City> cityList = new HashSet<>();

    @PreRemove
    public void deleteObjects(){
        if (addressList != null)
            this.addressList.forEach(address -> address.setProvince(null));
    }

}
