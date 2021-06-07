package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.InventoryUpdatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseDeletedMessage;
import com.thesis.gamamicroservices.productsview.model.Inventory;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.model.Warehouse;
import com.thesis.gamamicroservices.productsview.repository.ProductRepository;
import com.thesis.gamamicroservices.productsview.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryEventsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public void updateInventory(InventoryUpdatedMessage inventoryUpdated) {
        Product product = productRepository.findByProductId(inventoryUpdated.getProductId()).get();

        Optional<Inventory> matchingObject = product.getInventories().stream()
                .filter(i -> i.getInventoryId() == inventoryUpdated.getInventoryId())
                .findFirst();

        if(matchingObject.isPresent()) {
            matchingObject.get().setStockAmount(inventoryUpdated.getStockAmount());
        } else {
            Inventory newInventory = new Inventory(inventoryUpdated);
            product.getInventories().add(newInventory);
        }
        productRepository.save(product);

    }

    public void createWarehouse(WarehouseCreatedMessage warehouseCreated) {
        warehouseRepository.save(new Warehouse(warehouseCreated));
    }

    public void deleteWarehouse(WarehouseDeletedMessage warehouseDeleted) {
        warehouseRepository.deleteById(warehouseDeleted.getId());
    }
}
