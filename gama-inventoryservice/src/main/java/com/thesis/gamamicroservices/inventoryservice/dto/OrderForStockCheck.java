package com.thesis.gamamicroservices.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderForStockCheck {

    private int orderId;
    private Map<Integer,Integer> products_qty;


}
