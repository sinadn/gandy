package com.example.gandy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Factor {
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="store-sequence-generator")
//    @SequenceGenerator(name = "store-sequence-generator", sequenceName = "user_sequence", initialValue = 1000  , allocationSize=1)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_at;
    private int status;
    private String trackingCode;
    private String payWay;
    @ManyToOne
    private Users users;
    private String address;
    private String shipment;
    private long shipmentPrice;
    @ManyToOne
    private Payment payment;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "factor_shipmentStatus", joinColumns = @JoinColumn(name = "factor_id"), inverseJoinColumns = @JoinColumn(name = "shipmentstatus_id"))
    private Set<ShipmentStatus> shipmentStatus = new HashSet<>();

    @OneToMany(mappedBy = "factor", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Cart> cartList = new HashSet<>();





}
