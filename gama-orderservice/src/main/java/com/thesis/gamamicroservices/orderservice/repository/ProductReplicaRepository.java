package com.thesis.gamamicroservices.orderservice.repository;

import com.thesis.gamamicroservices.orderservice.model.foreign.ProductReplica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReplicaRepository extends CrudRepository<ProductReplica, Integer> {
    List<ProductReplica> findAll();
}