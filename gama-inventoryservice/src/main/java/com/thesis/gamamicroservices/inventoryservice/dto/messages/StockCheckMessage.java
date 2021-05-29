package com.thesis.gamamicroservices.inventoryservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockCheckMessage {
    int orderId;
    boolean stockAvailable;
}
