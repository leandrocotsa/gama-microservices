package com.thesis.gamamicroservices.productsview.dto.messages.inventory_service;

import com.thesis.gamamicroservices.inventoryservice.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseCreatedMessage {
    private int warehouseId;
    private String name;
    private String description;

    public WarehouseCreatedMessage(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.name = warehouse.getName();
        this.description = warehouse.getDescription();
    }

}
