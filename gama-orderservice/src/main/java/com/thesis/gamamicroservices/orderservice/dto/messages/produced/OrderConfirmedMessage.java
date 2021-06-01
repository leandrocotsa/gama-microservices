package com.thesis.gamamicroservices.orderservice.dto.messages.produced;


import com.thesis.gamamicroservices.orderservice.model.Order;
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

    public OrderConfirmedMessage(Order order) {
        this.orderId = order.getId();
        this.price = order.calculateTotalValueToPay();
    }
}
