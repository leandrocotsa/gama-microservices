package com.thesis.gamamicroservices.reviewservice.model;


import com.thesis.gamamicroservices.reviewservice.dto.ReviewSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ratingStars;
    private String comment;

    private int productId;
    private int userId;
    private String userName;

    public Review(ReviewSetDTO reviewSetDTO, int userId, int productId) {
        this.ratingStars = reviewSetDTO.getRatingStars();
        this.comment = reviewSetDTO.getComment();
        this.productId = productId;
        this.userId = userId;
    }
}
