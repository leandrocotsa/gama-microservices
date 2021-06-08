package com.thesis.gamamicroservices.ordersview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {
    private int productId;
    private String productName;
    private int qty;
    private double priceAtBuyTime;
}
