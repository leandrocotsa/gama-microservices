package com.thesis.gamamicroservices.productsview.dto.messages.promotion_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionDeletedMessage {
    int promotionId;
}
