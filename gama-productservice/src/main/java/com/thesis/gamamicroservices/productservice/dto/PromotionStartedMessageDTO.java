package com.thesis.gamamicroservices.productservice.dto;

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

}
