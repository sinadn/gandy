package com.example.gandy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentId;
    private Boolean isSuccessful;
    private String authority;
    private String saleReferenceId;
    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany(mappedBy = "payment" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Factor> factors;

    @PreRemove
    public void deleteObjects() {
        if (factors != null)
            this.factors.forEach(factor -> factor.setPayment(null));
    }


    }
