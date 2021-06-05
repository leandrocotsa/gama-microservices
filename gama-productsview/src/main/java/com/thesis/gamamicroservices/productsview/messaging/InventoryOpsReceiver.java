package com.thesis.gamamicroservices.productsview.messaging;

import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.InventoryUpdatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.ProductCreatedMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.InventoryEventsService;
import com.thesis.gamamicroservices.productsview.messaging.service.ProductsEventsService;
import com.thesis.gamamicroservices.productsview.repository.WarehouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="inventoryWarehouseProductsViewQueue")
public class InventoryOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(InventoryOpsReceiver.class);

    private static final String INVENTORY_UPDATED_LOG = "Inventory updated event. Inventory: {}";
    private static final String WAREHOUSE_CREATED_LOG = "Warehouse created event. Warehouse: {}";
    private static final String WAREHOUSE_DELETED_LOG = "Warehouse deleted event. Warehouse: {}";

    @Autowired
    InventoryEventsService inventoryEventsService;

    @RabbitHandler
    public void inventoryUpdated(InventoryUpdatedMessage inventoryUpdated) {
        //ProductReplica product = new ProductReplica(productCreated);
        logger.info(INVENTORY_UPDATED_LOG, inventoryUpdated.getProductId());
        inventoryEventsService.updateInventory(inventoryUpdated);
    }

    @RabbitHandler
    public void warehouseCreated(WarehouseCreatedMessage warehouseCreated) {
        inventoryEventsService.createWarehouse(warehouseCreated);
        logger.info(WAREHOUSE_CREATED_LOG, warehouseCreated.getWarehouseId());
    }

    @RabbitHandler
    public void warehouseDeleted(WarehouseDeletedMessage warehouseDeleted) {
        logger.info(WAREHOUSE_DELETED_LOG, warehouseDeleted.getId());
        inventoryEventsService.deleteWarehouse(warehouseDeleted);
    }
}
