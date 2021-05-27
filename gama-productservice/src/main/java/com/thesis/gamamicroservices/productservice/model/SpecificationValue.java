package com.thesis.gamamicroservices.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesis.gamamicroservices.productservice.dto.SpecificationValueSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="specification_value")
public class SpecificationValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    private String specificationName;
    private String value;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public SpecificationValue (SpecificationValueSetDTO specificationValueSetDTO) {
        this.specificationName = specificationValueSetDTO.getSpecificationName();
        this.value = specificationValueSetDTO.getSpecificationValue();
    }

}
