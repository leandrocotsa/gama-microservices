package com.thesis.gamamicroservices.orderservice.dto.messages.produced;

import com.thesis.gamamicroservices.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//para a view only
public class OrderStatusUpdateMessage {
    int orderId;
    String orderStatus; //rejected, expired, shipped

    public OrderStatusUpdateMessage(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus().name();
    }
}
