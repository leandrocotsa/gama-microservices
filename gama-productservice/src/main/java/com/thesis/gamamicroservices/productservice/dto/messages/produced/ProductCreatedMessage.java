package com.thesis.gamamicroservices.productservice.dto.messages.produced;

import com.thesis.gamamicroservices.productservice.model.Product;
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

    public ProductCreatedMessage(Product product, int brandId, int categoryId) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.promotionPrice = product.getPromotionPrice();
        this.weight = product.getWeight();
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

}
