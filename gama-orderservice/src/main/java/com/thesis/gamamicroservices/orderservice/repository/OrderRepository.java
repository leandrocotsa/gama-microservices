package com.thesis.gamamicroservices.orderservice.repository;


import com.thesis.gamamicroservices.orderservice.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();
    Page<Order> findAll(Specification<Order> specs, Pageable pageable);
}