package com.thesis.gamamicroservices.orderservice.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.orderservice.dto.*;
import com.thesis.gamamicroservices.orderservice.dto.messages.consumed.StockCheckMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.produced.OrderConfirmedMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.produced.OrderCreatedMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.produced.OrderStatusUpdateMessage;
import com.thesis.gamamicroservices.orderservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.orderservice.model.Order;
import com.thesis.gamamicroservices.orderservice.model.OrderItem;
import com.thesis.gamamicroservices.orderservice.model.OrderStatus;
import com.thesis.gamamicroservices.orderservice.model.Shipping;
import com.thesis.gamamicroservices.orderservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.orderservice.repository.OrderRepository;
import com.thesis.gamamicroservices.orderservice.repository.ProductReplicaRepository;
import com.thesis.gamamicroservices.orderservice.security.JwtTokenUtil;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange ordersExchange;
    private final OrderRepository orderRepository;
    private final ProductReplicaRepository productOrderServiceRepository;
    private final ShippingService shippingService;
    private final ObjectWriter objectWriter;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate, @Qualifier("ordersExchange") Exchange ordersExchange, OrderRepository orderRepository, ProductReplicaRepository productOrderServiceRepository, ShippingService shippingService, ObjectWriter objectWriter, JwtTokenUtil jwtTokenUtil) {
        this.rabbitTemplate = rabbitTemplate;
        this.ordersExchange = ordersExchange;
        this.orderRepository = orderRepository;
        this.productOrderServiceRepository = productOrderServiceRepository;
        this.shippingService = shippingService;
        this.objectWriter = objectWriter;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    public Order getOrderById(int id) throws NoDataFoundException { //maybe verificar o user com o authroization token aqui?
        Optional<Order> order = this.orderRepository.findById(id);
        if(order.isPresent()) {
            return order.get();
        }
        else {
            throw new NoDataFoundException("There's no order with id " + id);
        }
    }

    public void deleteOrder(String authorizationToken, int id) throws NoDataFoundException {
        if(orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new NoDataFoundException ("There's no Warehouse with that id");
        }
    }

    public void createOrder(OrderSetDTO orderSetDTO, String authorizationToken) throws NoDataFoundException {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        Order newOrder = new Order(orderSetDTO, userId, email);

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemSetDTO orderItemDTO : orderSetDTO.getOrderItems()){
            orderItems.add(new OrderItem(orderItemDTO, getProductById(orderItemDTO.getProductId())));
        }

        newOrder.setAllOrderItems(orderItems);
        Shipping shipping = new Shipping(shippingService.calculateShippingValue(newOrder.getTotalWeight(), orderSetDTO.getCountry()), "notes", orderSetDTO.getAddress(), orderSetDTO.getCountry());
        newOrder.addShippingToOrder(shipping);
        orderRepository.save(newOrder);
        rabbitTemplate.convertAndSend(ordersExchange.getName(), RoutingKeys.ORDER_CREATED.getNotation(), new OrderCreatedMessage(newOrder));

    }


    public ProductReplica getProductById(int id) throws NoDataFoundException {
        Optional<ProductReplica> product = this.productOrderServiceRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }
        else {
            throw new NoDataFoundException("There's no product with id " + id);
        }
    }

    public void processStock(StockCheckMessage stockCheckMessage) {
        Order o;
        try {
            o = getOrderById(stockCheckMessage.getOrderId());
        } catch (NoDataFoundException e) {
            e.printStackTrace();
            return;
        }
        if(stockCheckMessage.isStockAvailable()) {
            o.setOrderStatus(OrderStatus.PENDING_PAYMENT);
            orderRepository.save(o);
            rabbitTemplate.convertAndSend(ordersExchange.getName(), RoutingKeys.ORDER_CONFIRMED.getNotation(), new OrderConfirmedMessage(o));
        } else {
            o.setOrderStatus(OrderStatus.REJECTED);
            orderRepository.save(o);
            rabbitTemplate.convertAndSend(ordersExchange.getName(), RoutingKeys.ORDER_UPDATED.getNotation(), new OrderStatusUpdateMessage(o));
        }
    }


    public void paymentConfirmed(int orderId) throws NoDataFoundException {
        Order order = getOrderById(orderId);
        order.setOrderStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
        rabbitTemplate.convertAndSend(ordersExchange.getName(), RoutingKeys.ORDER_UPDATED.getNotation(), new OrderStatusUpdateMessage(order));
    }


    //scheduled job calls this method if order hasnt been approved for 24 hours
    public void expireOrder(int orderId) throws NoDataFoundException {
        Order order = getOrderById(orderId);
        order.setOrderStatus(OrderStatus.EXPIRED);

    }


}
