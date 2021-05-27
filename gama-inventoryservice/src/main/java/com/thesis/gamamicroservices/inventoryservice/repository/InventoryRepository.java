package com.thesis.gamamicroservices.inventoryservice.repository;

import com.thesis.gamamicroservices.inventoryservice.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    List<Inventory> findAllByProductId(int productID);
    List<Inventory> findAllByWarehouseId(int warehouseID);
    Optional<Inventory> findByProductIdAndWarehouseId(int productID, int warehouseID);

}