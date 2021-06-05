package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.InventoryUpdatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Inventory {
    private int inventoryId;
    private int warehouseId;
    private int stockAmount;

    public Inventory(InventoryUpdatedMessage inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.warehouseId = inventory.getWarehouseId();
        this.stockAmount = inventory.getStockAmount();
    }
}
