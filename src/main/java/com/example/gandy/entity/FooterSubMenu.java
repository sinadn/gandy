package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FooterSubMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    @Nullable
    private String content;
    @ManyToOne
    private FooterMenu footerMenu;
    @Nullable
    private long amount;
    @Nullable
    @ManyToOne
    private ProductType productType;
    @Nullable
    @ManyToOne
    private Tag productTag;
    @Nullable
    @ManyToOne
    private AttributeOption attributeOption;
}
