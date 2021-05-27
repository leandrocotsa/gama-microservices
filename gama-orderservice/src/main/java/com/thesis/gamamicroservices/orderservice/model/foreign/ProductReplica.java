package com.thesis.gamamicroservices.orderservice.model.foreign;

import com.thesis.gamamicroservices.orderservice.dto.messages.ProductCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="product")
public class ProductReplica {
    @Id
    private int id;
    private String name;
    private Double price;
    private Double promotionPrice;
    private float weight;

    public ProductReplica(ProductCreatedMessage productCreatedDTO) {
        this.id = productCreatedDTO.getId();
        this.name = productCreatedDTO.getName();
        this.price = productCreatedDTO.getPrice();
        this.promotionPrice = productCreatedDTO.getPromotionPrice();
        this.weight = productCreatedDTO.getWeight();
    }

}
