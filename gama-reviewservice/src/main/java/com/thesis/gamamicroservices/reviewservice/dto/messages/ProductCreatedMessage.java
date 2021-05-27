package com.thesis.gamamicroservices.reviewservice.dto.messages;

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
    private int categoryId;
    //specification values somehow, para a view dos produtos, é preciso
    //os outros service stambem recebem mas ignoram, pq tem aquilo do fail a false


}
