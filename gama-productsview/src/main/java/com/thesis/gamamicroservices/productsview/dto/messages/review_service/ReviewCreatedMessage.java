package com.thesis.gamamicroservices.productsview.dto.messages.review_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreatedMessage {
    private int reviewId;
    private int userId;
    private int productId;
    private int ratingStars;
    private String comment;
}
