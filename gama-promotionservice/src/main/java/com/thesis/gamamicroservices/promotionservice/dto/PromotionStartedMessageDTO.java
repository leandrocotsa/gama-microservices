package com.thesis.gamamicroservices.promotionservice.dto;

import com.thesis.gamamicroservices.promotionservice.model.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionStartedMessageDTO {
    private List<Integer> productsIds;
    private int discountAmount;

    public PromotionStartedMessageDTO(Promotion promotion) {
        this.productsIds = promotion.getProductsIds();
        this.discountAmount = promotion.getDiscountAmount();
    }
}
