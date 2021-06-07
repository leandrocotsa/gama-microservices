package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewDeletedMessage;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.model.Review;
import com.thesis.gamamicroservices.productsview.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewEventsService {

    @Autowired
    ProductRepository productRepository;

    public void createReview(ReviewCreatedMessage reviewCreated) {
        Product product = productRepository.findByProductId(reviewCreated.getProductId()).get();
        Review newReview = new Review(reviewCreated);
        product.getReviews().add(newReview);

        productRepository.save(product);
    }


    //testar se assim funciona ou se tenho de ir ao produto e retirar a review da lista
    public void deleteReview(ReviewDeletedMessage reviewDeleted) {
        Product product = productRepository.findByProductId(reviewDeleted.getProductId()).get();
        product.getReviews().removeIf(r -> r.getReviewId() == reviewDeleted.getReviewId());
        productRepository.save(product);
    }
}
