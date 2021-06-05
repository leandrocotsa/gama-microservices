package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewDeletedMessage;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.model.Review;
import com.thesis.gamamicroservices.productsview.repository.ProductRepository;
import com.thesis.gamamicroservices.productsview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewEventsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public void createReview(ReviewCreatedMessage reviewCreated) {
        Product product = productRepository.findByProductId(reviewCreated.getProductId()).get();
        Review newReview = new Review(reviewCreated);
        product.getReviews().add(newReview);

        productRepository.save(product);
    }


    //testar se assim funciona ou se tenho de ir ao produto e retirar a review da lista
    public void deleteReview(ReviewDeletedMessage reviewDeleted) {
        reviewRepository.deleteById(String.valueOf(reviewDeleted.getReviewId()));
    }
}
