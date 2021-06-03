package com.thesis.gamamicroservices.productsview.dto.messages.promotion_service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionUpdatedMessage {
    List<Integer> allProducts;
}
