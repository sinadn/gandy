package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Carousels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer row_num;


    @ManyToMany
    @JoinTable(
            name = "carousel_type",
            joinColumns = @JoinColumn(name = "carousel_id"),
            inverseJoinColumns = @JoinColumn(name = "productType_id"))
    private Set<ProductType> options = new HashSet<>();;



//    @ManyToOne
//    @Nullable
//    private ProductType option1;
//    @ManyToOne
//    @Nullable
//    private ProductType option2;
//    @ManyToOne
//    @Nullable
//    private ProductType option3;




    private Integer main_Option;

    private String per_name;
    private String en_name;
    public Boolean hide;








}


