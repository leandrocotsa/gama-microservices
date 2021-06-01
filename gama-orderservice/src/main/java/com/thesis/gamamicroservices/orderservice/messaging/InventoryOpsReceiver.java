package com.thesis.gamamicroservices.orderservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.orderservice.dto.messages.consumed.StockCheckMessage;
import com.thesis.gamamicroservices.orderservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="stockInventoryServiceQueue")
public class InventoryOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String STOCK_CHECKED_LOG = "Order checked event. Stock was checked for Order: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    // lets a single listener invoke different methods, based on the payload type of the incoming message (function arguments)
    //queria filtrar por routing key, neste momento tá tudo product.* e queria método para product.x e product.y
    @RabbitHandler
    public void stockChecked(StockCheckMessage stockCheckMessage) {
        logger.info(STOCK_CHECKED_LOG, stockCheckMessage.getOrderId());
        eventsService.stockChecked(stockCheckMessage);
    }

}
