package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review {
    private int reviewId;
    private int userId; //nao tenho nome aqui, fica anónimo?
    private int ratingStars;
    private String comment;

    public Review(ReviewCreatedMessage review){
        this.reviewId = review.getReviewId();
        this.userId = review.getUserId();
        this.ratingStars = review.getRatingStars();
        this.comment = review.getComment();
    }
}
