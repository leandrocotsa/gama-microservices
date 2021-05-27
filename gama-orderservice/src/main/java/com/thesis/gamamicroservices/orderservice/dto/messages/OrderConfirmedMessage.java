package com.thesis.gamamicroservices.orderservice.dto.messages;


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
    private int id;
    private Double price;

    public OrderConfirmedMessage(Order order) {
        this.id = order.getId();
        this.price = order.calculateTotalValueToPay();
    }
}
