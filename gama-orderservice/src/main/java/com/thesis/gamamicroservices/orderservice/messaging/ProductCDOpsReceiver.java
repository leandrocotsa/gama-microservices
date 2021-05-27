package com.thesis.gamamicroservices.orderservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.orderservice.dto.messages.ProductCreatedMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.ProductDeletedMessage;
import com.thesis.gamamicroservices.orderservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.orderservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="productsCDOrderServiceQueue")
public class ProductCDOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String PRODUCT_CREATED_LOG = "Product created event. Product: {}";
    private static final String PRODUCT_DELETED_LOG = "Product deleted event. Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;

    @RabbitHandler
    public void productCreated(ProductCreatedMessage productCreated) {
        ProductReplica product = new ProductReplica(productCreated);
        logger.info(PRODUCT_CREATED_LOG, productCreated.getId());
        eventsService.productCreated(product);
    }

    @RabbitHandler
    public void productDeleted(ProductDeletedMessage productDeleted) {
        logger.info(PRODUCT_DELETED_LOG, productDeleted.getId());
        eventsService.productDeleted(productDeleted.getId());
    }



}
