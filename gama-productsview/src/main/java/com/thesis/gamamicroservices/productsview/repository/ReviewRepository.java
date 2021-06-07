package com.thesis.gamamicroservices.productsview.repository;

import com.thesis.gamamicroservices.productsview.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, Integer> {
}
