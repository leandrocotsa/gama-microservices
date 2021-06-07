package com.thesis.gamamicroservices.productsview.repository;

import com.thesis.gamamicroservices.productsview.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, Integer> {
}
