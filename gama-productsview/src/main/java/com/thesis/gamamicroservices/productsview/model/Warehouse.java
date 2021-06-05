package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "warehouses")
public class Warehouse {
    private int warehouseId;
    private String name;
    private String description;

    public Warehouse(WarehouseCreatedMessage warehouseCreated) {
        this.warehouseId = warehouseCreated.getWarehouseId();
        this.name = warehouseCreated.getName();
        this.description = warehouseCreated.getDescription();
    }
}
