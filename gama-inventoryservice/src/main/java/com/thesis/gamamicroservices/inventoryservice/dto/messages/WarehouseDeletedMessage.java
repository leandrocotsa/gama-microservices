package com.thesis.gamamicroservices.inventoryservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseDeletedMessage {
    private int id;
}
