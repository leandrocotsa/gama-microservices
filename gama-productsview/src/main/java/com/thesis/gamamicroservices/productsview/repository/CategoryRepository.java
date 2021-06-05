package com.thesis.gamamicroservices.productsview.repository;

import com.thesis.gamamicroservices.productsview.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
