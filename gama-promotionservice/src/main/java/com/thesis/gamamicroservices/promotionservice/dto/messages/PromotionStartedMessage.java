package com.thesis.gamamicroservices.promotionservice.dto.messages;

import com.thesis.gamamicroservices.promotionservice.model.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionStartedMessage {
    private List<Integer> productsIds;
    private int discountAmount;
    private int promotionId;

    public PromotionStartedMessage(Promotion promotion) {
        this.productsIds = promotion.getProductsIds();
        this.discountAmount = promotion.getDiscountAmount();
        this.promotionId = promotion.getId();
    }
}
