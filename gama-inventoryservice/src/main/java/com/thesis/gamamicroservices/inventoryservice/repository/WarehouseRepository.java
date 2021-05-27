package com.thesis.gamamicroservices.inventoryservice.repository;

import com.thesis.gamamicroservices.inventoryservice.model.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Integer> {
    Optional<Warehouse> findByName(String name);
    Optional<Warehouse> findById(int id);
    

}
