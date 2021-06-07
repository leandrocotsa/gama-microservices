package com.thesis.gamamicroservices.productsview.repository;

import com.thesis.gamamicroservices.productsview.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionRepository extends MongoRepository<Promotion, Integer> {


}
