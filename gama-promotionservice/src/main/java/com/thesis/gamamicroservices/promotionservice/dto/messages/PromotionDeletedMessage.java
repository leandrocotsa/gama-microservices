package com.thesis.gamamicroservices.promotionservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionDeletedMessage {
    int promotionId;
}
