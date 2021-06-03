package com.thesis.gamamicroservices.productsview.dto.messages.inventory_service;

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

}
