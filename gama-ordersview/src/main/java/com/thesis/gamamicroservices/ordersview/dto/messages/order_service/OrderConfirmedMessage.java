package com.thesis.gamamicroservices.ordersview.dto.messages.order_service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//para o payment e view
//fanout
public class OrderConfirmedMessage {
    private int orderId;
    private Double price;

}
