package com.thesis.gamamicroservices.productsview.dto.messages.promotion_service;

import com.thesis.gamamicroservices.promotionservice.model.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionCreatedMessage {
    private int promotionId;
    private String name;
    private String description;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
    private List<Integer> productsIDs;

    public PromotionCreatedMessage(Promotion promotion) {
        this.promotionId = promotion.getId();
        this.name = promotion.getName();
        this.description = promotion.getDescription();
        this.discountAmount = promotion.getDiscountAmount();
        this.startDate = promotion.getStartDate();
        this.endDate = promotion.getEndDate();
        this.productsIDs = promotion.getProductsIds();
    }
}
