package com.thesis.gamamicroservices.orderservice.dto.messages.consumed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreatedMessage {
    private int id;
    private String name;
    private Double price;
    private Double promotionPrice;
    private float weight;
    private int brandId;
    private String brandName;
    private int categoryId;
    private String categoryName;


}
