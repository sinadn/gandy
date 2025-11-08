package com.example.gandy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AttributeType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String attributeType;
    @Nullable
    @ManyToOne
    private ProductType productType;
    @Nullable
    private Integer sort;


    @OneToMany(mappedBy = "attributeType" ,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST , CascadeType.REMOVE})
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<AttributeOption> attributeOptions = new HashSet<>();



    @PreRemove
    public void deleteObjects(){
        if (attributeOptions != null)
            this.attributeOptions.forEach(attributeOption -> attributeOption.setAttributeType(null));
    }


}
