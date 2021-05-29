package com.thesis.gamamicroservices.productservice.dto.messages;

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

}
