package com.thesis.gamamicroservices.productsview.dto.messages.inventory_service;

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


}
