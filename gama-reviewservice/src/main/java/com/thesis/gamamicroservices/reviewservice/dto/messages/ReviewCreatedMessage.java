package com.thesis.gamamicroservices.reviewservice.dto.messages;

import com.thesis.gamamicroservices.reviewservice.model.Review;
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

    public ReviewCreatedMessage(Review review) {
        this.reviewId = review.getId();
        this.userId = review.getUserId();
        this.productId = review.getProductId();
        this.ratingStars = review.getRatingStars();
        this.comment = review.getComment();
    }
}
