package com.thesis.gamamicroservices.reviewservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    @Autowired
    ReviewService reviewService;

    public void productDeleted(int productId) {
        reviewService.deleteReviewByProductId(productId);
    }

    public void userDeleted(int userId) {
        reviewService.deleteReviewByUserId(userId);
    }



}
