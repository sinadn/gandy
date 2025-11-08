package com.example.gandy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WebInfo {
    @Id
    private Long id;
    private String name;
    private String logo;
    private String tell;
    private String mobile;
    private String instagram;
    private String whatsApp;
    private String telegram;
    private String email;
    private String workTime;
    private String address;
    private String aboutUs;

}
