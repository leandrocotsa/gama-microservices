package com.thesis.gamamicroservices.productsview.dto.messages.product_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCreatedMessage {
    private int id;
    private String name;
    private String typicalSpecifications;

}
