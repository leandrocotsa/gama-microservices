package com.thesis.gamamicroservices.reviewservice.repository;

import com.thesis.gamamicroservices.reviewservice.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    Optional<Review> findById(int id);
    void deleteAllByProductId(int productId);
    void deleteAllByUserId(int userId);

}
