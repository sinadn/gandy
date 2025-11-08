package com.example.gandy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AttributeOption {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String filterName;

    private String attributeOption;

    @ManyToOne
    private AttributeType attributeType;

    @OneToMany(mappedBy = "attributeOption" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Category> categoryList = new HashSet<>();

    @OneToMany(mappedBy = "attributeOption" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Cover> coverList = new HashSet<>();

    @OneToMany(mappedBy = "attributeOption" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductConfig> productConfigs = new HashSet<>();

    @OneToMany(mappedBy = "attributeOption" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Slider> sliders = new HashSet<>();

    @OneToMany(mappedBy = "attributeOption" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<FooterSubMenu> footerSubMenus = new HashSet<>();


    @PreRemove
    public void deleteObjects(){
        if (sliders != null)
            this.sliders.forEach(attributeOption -> attributeOption.setAttributeOption(null));
        if (coverList != null)
            this.coverList.forEach(attributeOption -> attributeOption.setAttributeOption(null));
        if (categoryList != null)
            this.categoryList.forEach(attributeOption -> attributeOption.setAttributeOption(null));
        if (footerSubMenus != null)
            this.footerSubMenus.forEach(footerSubMenu -> footerSubMenu.setAttributeOption(null));
    }




}
