package com.thesis.gamamicroservices.inventoryservice.repository;

import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReplicaRepository extends CrudRepository<ProductReplica, Integer> {
    List<ProductReplica> findAll();
}