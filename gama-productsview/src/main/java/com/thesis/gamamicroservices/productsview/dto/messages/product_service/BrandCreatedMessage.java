package com.thesis.gamamicroservices.productsview.dto.messages.product_service;

import com.thesis.gamamicroservices.productservice.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandCreatedMessage {
    private int id;
    private String name;

    public BrandCreatedMessage(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}