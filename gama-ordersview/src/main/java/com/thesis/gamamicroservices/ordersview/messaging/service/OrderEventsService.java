package com.thesis.gamamicroservices.ordersview.messaging.service;

import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderConfirmedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderCreatedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderStatusUpdateMessage;
import org.springframework.stereotype.Service;

@Service
public class OrderEventsService {


    public void createOrder(OrderCreatedMessage orderCreated) {
    }

    public void confirmOrder(OrderConfirmedMessage orderConfirmed) {
    }

    public void updateOrderStatus(OrderStatusUpdateMessage orderStatusUpdate) {
    }

}
