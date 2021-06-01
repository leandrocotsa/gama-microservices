package com.thesis.gamamicroservices.orderservice.dto.messages.consumed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionPriceMessage {
    private Map<Integer,Double> productsIds_and_prices;

}
