package com.thesis.gamamicroservices.orderservice.dto.messages.consumed;

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
