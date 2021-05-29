package com.thesis.gamamicroservices.inventoryservice.service;

import com.thesis.gamamicroservices.inventoryservice.dto.OrderForStockCheck;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.OrderCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.StockCheckMessage;
import com.thesis.gamamicroservices.inventoryservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.inventoryservice.repository.ProductReplicaRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    private final ProductReplicaRepository productRepository;
    private final InventoryService inventoryService;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Autowired
    public EventsService(ProductReplicaRepository productRepository, InventoryService inventoryService, RabbitTemplate rabbitTemplate, @Qualifier("stockCheckExchange") DirectExchange exchange) {
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


    public void orderCreated(OrderCreatedMessage order) {
        //Integer[] stockResponse = new Integer[2];
        StockCheckMessage stockCheckMessage = new StockCheckMessage();
        stockCheckMessage.setOrderId(order.getOrderId());
        try {
            inventoryService.reserveStock(order);
            stockCheckMessage.setStockAvailable(true);
        } catch (NoStockException e) {
            stockCheckMessage.setStockAvailable(false);
        } catch (Exception e) {
            stockCheckMessage.setStockAvailable(false);
            e.printStackTrace();
        } finally {
            rabbitTemplate.convertAndSend(exchange.getName(), RoutingKeys.STOCK_CHECK.getNotation(), stockCheckMessage);
        }
    }

}
