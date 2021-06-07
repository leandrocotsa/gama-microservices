package com.thesis.gamamicroservices.productsview.dto.messages.review_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDeletedMessage {
    int reviewId;
    int productId;
}
