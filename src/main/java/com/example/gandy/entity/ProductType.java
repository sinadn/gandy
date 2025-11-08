package com.example.gandy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;
    public String name;
    @Nullable
    public String img;
    @Nullable
    @ManyToOne
    @JsonIgnoreProperties
    private ProductType parentProductType;


    @ManyToMany(mappedBy = "options" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    private Set<Carousels> carousels = new HashSet<>();


    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Category> categoryList;

    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Cover> coverList;

    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<MainWare> mainWares;

    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Product> products;

    @OneToMany(mappedBy = "parentProductType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<ProductType> productTypes;

    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Slider> sliders;

    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<TagAmountBox> tagAmountBoxes;


    @OneToMany(mappedBy = "productType" ,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<FooterSubMenu> footerSubMenus;

    @OneToMany(mappedBy = "productType" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<AttributeType> attributeTypes;


//    @OneToMany(mappedBy = "option1" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
//    @com.fasterxml.jackson.annotation.JsonIgnore
//    private Set<Carousels> carousels1;
//
//
//    @OneToMany(mappedBy = "option2" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
//    @com.fasterxml.jackson.annotation.JsonIgnore
//    private Set<Carousels> carousels2;
//
//
//    @OneToMany(mappedBy = "option3" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
//    @com.fasterxml.jackson.annotation.JsonIgnore
//    private Set<Carousels> carousels3;


    @PreRemove
    public void deleteObjects(){
        if (categoryList != null)
            this.categoryList.forEach(category -> category.setProductType(null));
        if (coverList != null)
            this.coverList.forEach(cover -> cover.setProductType(null));
        if (mainWares != null)
            this.mainWares.forEach(mainWare -> mainWare.setProductType(null));
        if (products != null)
            this.products.forEach(product -> product.setProductType(null));
        if (sliders != null)
            this.sliders.forEach(slider -> slider.setProductType(null));
        if (productTypes != null)
            this.productTypes.forEach(productType -> productType.setParentProductType(null));
        if (tagAmountBoxes != null)
            this.tagAmountBoxes.forEach(tagAmountBox -> tagAmountBox.setProductType(null));
        if (footerSubMenus != null)
            this.footerSubMenus.forEach(footerSubMenu -> footerSubMenu.setProductType(null));
        if (attributeTypes != null)
            this.attributeTypes.forEach(attributeType -> attributeType.setProductType(null));
        if (carousels != null)
            this.carousels.forEach(carousel -> carousel.getOptions().remove(this));

//        if (carousels != null)
//            this.carousels.forEach(carousel -> carousel.setOptions(null));
//        if (carousels2 != null)
//            this.carousels2.forEach(carousel -> carousel.setOption2(null));
//        if (carousels3 != null)
//            this.carousels3.forEach(carousel -> carousel.setOption3(null));

    }

}
