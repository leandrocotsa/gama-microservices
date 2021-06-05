package com.thesis.gamamicroservices.productsview.repository;

import com.thesis.gamamicroservices.productsview.model.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<Brand, String> {
}
