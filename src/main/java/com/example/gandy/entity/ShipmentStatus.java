package com.example.gandy.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShipmentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EShipmentStatus name;

    public ShipmentStatus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EShipmentStatus getName() {
        return name;
    }

    public void setName(EShipmentStatus name) {
        this.name = name;
    }
// getters and setters
}