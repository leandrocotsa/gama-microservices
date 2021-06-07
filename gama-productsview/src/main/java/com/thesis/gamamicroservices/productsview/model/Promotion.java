package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "promotions")
public class Promotion {
    @Id
    private int promotionId;
    private String name;
    private String description;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
    private List<Integer> products; //se quiser fazer query tenho que fazer um join manual

    public Promotion(PromotionCreatedMessage promotionCreated) {
        this.promotionId = promotionCreated.getPromotionId();
        this.name = promotionCreated.getName();
        this.description = promotionCreated.getDescription();
        this.discountAmount = promotionCreated.getDiscountAmount();
        this.startDate = promotionCreated.getStartDate();
        this.endDate = promotionCreated.getEndDate();
        this.products = promotionCreated.getProductsIDs();
    }

}
