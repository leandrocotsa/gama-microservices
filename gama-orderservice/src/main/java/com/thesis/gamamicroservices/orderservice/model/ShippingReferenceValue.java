package com.thesis.gamamicroservices.orderservice.model;


import com.thesis.gamamicroservices.orderservice.dto.ShippingReferenceSetDTO;
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
@Table(name="shipping_reference_value")
public class ShippingReferenceValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double costPerkg;
    private String country;


    //para evitar ter construtor sem id explicitio podia criar uma superclass entity que so tinha id e todas as entities extendem e o all args ja funciona sem id
    public ShippingReferenceValue(Double costPerkg, String country) {
        this.costPerkg = costPerkg;
        this.country = country;
    }

    public ShippingReferenceValue(ShippingReferenceSetDTO shippingReferenceSetDTO) {
        this.costPerkg = shippingReferenceSetDTO.getCostPerkg();
        this.country = shippingReferenceSetDTO.getCountry();
    }

}
