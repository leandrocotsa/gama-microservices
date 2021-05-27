package com.thesis.gamamicroservices.reviewservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewSetDTO {
    private int ratingStars;
    private String comment;

}
