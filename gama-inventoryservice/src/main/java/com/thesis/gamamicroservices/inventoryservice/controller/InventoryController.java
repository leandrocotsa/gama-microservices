package com.thesis.gamamicroservices.inventoryservice.controller;


import com.thesis.gamamicroservices.inventoryservice.dto.WarehouseSetDTO;
import com.thesis.gamamicroservices.inventoryservice.service.AlreadyExistsException;
import com.thesis.gamamicroservices.inventoryservice.service.InventoryService;
import com.thesis.gamamicroservices.inventoryservice.service.NoDataFoundException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventories")
@Api(tags = "InventoryController")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private static final String INVENTORY_UPDATED_LOG = "Inventory updated for product {} and warehouse {}";
    private static final String WAREHOUSE_CREATED_LOG = "Warehouse: {} was created";
    private static final String WAREHOUSE_DELETED_LOG = "Warehouse: {} was deleted";
    private static final String WAREHOUSE_UPDATED_LOG = "Warehouse: {} was updated";

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/manual")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void editStock(
            @RequestParam("product_id") int product_id,
            @RequestParam("warehouse_id") int warehouse_id,
            @RequestParam("qty") int qty) throws NoDataFoundException {

        this.inventoryService.editStock(product_id, warehouse_id, qty);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void addStock(
            @RequestParam("product_id") int product_id,
            @RequestParam("warehouse_id") int warehouse_id,
            @RequestParam("qty") int qty) throws NoDataFoundException {

        this.inventoryService.addStock(product_id, warehouse_id, qty);
        logger.info(INVENTORY_UPDATED_LOG, product_id, warehouse_id);
    }


    @PostMapping(path="/warehouses")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createWarehouse(@RequestBody WarehouseSetDTO warehouseSetDTO) throws AlreadyExistsException {
        this.inventoryService.createWarehouse(warehouseSetDTO);
        logger.info(WAREHOUSE_CREATED_LOG, warehouseSetDTO);
    }

    @DeleteMapping(path="/warehouses/{warehouseID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWarehouse(@PathVariable int warehouseID) throws NoDataFoundException {
        this.inventoryService.deleteWarehouse(warehouseID);
        logger.info(WAREHOUSE_DELETED_LOG, warehouseID);
    }

    @PatchMapping(path="/warehouses/{warehouseID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void editWarehouse(@RequestBody Map<String, Object> updates, @PathVariable("warehouseID") int id) throws NoDataFoundException {
        this.inventoryService.editWarehouse(updates, id);
        logger.info(WAREHOUSE_UPDATED_LOG, id);
    }


}
