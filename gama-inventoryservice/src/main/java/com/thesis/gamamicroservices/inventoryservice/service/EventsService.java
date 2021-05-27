package com.thesis.gamamicroservices.inventoryservice.service;

import com.thesis.gamamicroservices.inventoryservice.dto.OrderForStockCheck;
import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.inventoryservice.repository.ProductReplicaRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    private final ProductReplicaRepository productRepository;
    private final InventoryService inventoryService;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Autowired
    public EventsService(ProductReplicaRepository productRepository, InventoryService inventoryService, RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void productCreated(ProductReplica product) {
        productRepository.save(product);
    }

    public void productDeleted(int productId) {
        productRepository.deleteById(productId);
    }


    public void orderCreated(OrderForStockCheck order) {
        Integer[] stockResponse = new Integer[2];
        try {
            inventoryService.reserveStock(order);
            stockResponse[0] = order.getOrderId();
            stockResponse[1] = 1;
            rabbitTemplate.convertAndSend(exchange.getName(), "stock", stockResponse);
        } catch (NoStockException e) {
            stockResponse[0] = order.getOrderId();
            stockResponse[1] = 0;
            rabbitTemplate.convertAndSend(exchange.getName(), "stock", stockResponse);
        } catch (Exception e) {
            stockResponse[0] = order.getOrderId();
            stockResponse[1] = 0;
            rabbitTemplate.convertAndSend(exchange.getName(), "stock", stockResponse);
            e.printStackTrace();
        }
    }

}
