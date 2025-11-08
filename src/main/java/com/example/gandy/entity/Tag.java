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
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String tag;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductTag> productTags = new HashSet<>();

    @OneToMany(mappedBy = "productTag" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "productTag" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Cover> coverList = new HashSet<>();

    @OneToMany(mappedBy = "productTag" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Slider> slider = new HashSet<>();

    @OneToMany(mappedBy = "productTag" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<TagAmountBox> tagAmountBoxes = new HashSet<>();


    @OneToMany(mappedBy = "productTag" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<FooterSubMenu> footerSubMenus = new HashSet<>();


    @PreRemove
    public void deleteObjects(){
        if (categories != null)
            this.categories.forEach(category -> category.setProductTag(null));
        if (coverList != null)
            this.coverList.forEach(cover -> cover.setProductTag(null));
        if (slider != null)
            this.slider.forEach(cover -> cover.setProductTag(null));
        if (tagAmountBoxes != null)
            this.tagAmountBoxes.forEach(tagAmountBox -> tagAmountBox.setProductTag(null));
        if (footerSubMenus != null)
            this.footerSubMenus.forEach(footerSubMenu -> footerSubMenu.setProductTag(null));

    }
}
