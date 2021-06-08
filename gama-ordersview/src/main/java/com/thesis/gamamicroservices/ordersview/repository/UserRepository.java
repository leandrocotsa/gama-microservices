package com.thesis.gamamicroservices.ordersview.repository;

import com.thesis.gamamicroservices.ordersview.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
}
