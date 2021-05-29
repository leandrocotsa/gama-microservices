package com.thesis.gamamicroservices.inventoryservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.inventoryservice.dto.OrderForStockCheck;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.OrderCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="ordersInventoryServiceQueue")
public class OrderOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(OrderOpsReceiver.class);

    private static final String ORDER_CREATED_LOG = "Order created event received. Checking stock for Order: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    // lets a single listener invoke different methods, based on the payload type of the incoming message (function arguments)
    //queria filtrar por routing key, neste momento tá tudo product.* e queria método para product.x e product.y
    @RabbitHandler
    public void orderCreated(OrderCreatedMessage orderCreated) {
        eventsService.orderCreated(orderCreated);
        logger.info(ORDER_CREATED_LOG, orderCreated.getOrderId());
        /**
        try {
            OrderForStockCheck order = objectMapper.readValue(orderJSON, OrderForStockCheck.class);
            logger.info(ORDER_CREATED_LOG, order.getOrderId());
            eventsService.orderCreated(order);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
         **/
    }



}
