package com.thesis.gamamicroservices.inventoryservice.dto.messages;

import com.thesis.gamamicroservices.inventoryservice.model.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryUpdatedMessage {
    int inventoryId;
    int warehouseId;
    int productId;
    int stockAmount;

    public InventoryUpdatedMessage(Inventory inventory) {
        this.inventoryId = inventory.getId();
        this.warehouseId = inventory.getWarehouse().getId();
        this.productId = inventory.getProduct().getId();
        this.stockAmount = inventory.getStockAmount();
    }
}
