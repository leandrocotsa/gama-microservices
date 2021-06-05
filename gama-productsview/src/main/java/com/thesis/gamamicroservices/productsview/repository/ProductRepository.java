package com.thesis.gamamicroservices.productsview.repository;


import com.thesis.gamamicroservices.productsview.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByProductId(final int id);
    /**
    @Query("{'address.country': ?0}")
    List<Product> findByCountry(final String country);
    **/
}