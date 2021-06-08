package com.thesis.gamamicroservices.ordersview.messaging.service;

import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderConfirmedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderCreatedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderStatusUpdateMessage;
import com.thesis.gamamicroservices.ordersview.model.Order;
import com.thesis.gamamicroservices.ordersview.model.OrderItem;
import com.thesis.gamamicroservices.ordersview.model.Shipping;
import com.thesis.gamamicroservices.ordersview.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderEventsService {

    @Autowired
    OrderRepository orderRepository;


    public void createOrder(OrderCreatedMessage orderCreated) {
        Order order = new Order(orderCreated);
        order.setShipping(new Shipping(orderCreated.getShippingId(), orderCreated.getShippingCost(), orderCreated.getShippingNotes(), orderCreated.getShippingCountry(), orderCreated.getShippingAddress()));
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : orderCreated.getProducts_qty().entrySet()) {
            int productId = entry.getKey();
            String productName = orderCreated.getProducts_name().get(entry.getKey());
            int qty = orderCreated.getProducts_qty().get(entry.getKey());
            Double priceAtBuyTime = orderCreated.getProducts_price().get(entry.getKey());
            OrderItem orderItem = new OrderItem(productId, productName, qty, priceAtBuyTime);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    public void confirmOrder(OrderConfirmedMessage orderConfirmed) {
        Order order = orderRepository.findById(orderConfirmed.getOrderId()).get();
        order.setOrderStatus("PENDING_PAYMENT");
        orderRepository.save(order);
    }

    public void updateOrderStatus(OrderStatusUpdateMessage orderStatusUpdate) {
        Order order = orderRepository.findById(orderStatusUpdate.getOrderId()).get();
        order.setOrderStatus(orderStatusUpdate.getOrderStatus());
        orderRepository.save(order);
    }

}
