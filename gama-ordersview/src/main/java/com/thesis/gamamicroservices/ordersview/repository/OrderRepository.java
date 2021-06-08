package com.thesis.gamamicroservices.ordersview.repository;

import com.thesis.gamamicroservices.ordersview.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Integer> {
}
