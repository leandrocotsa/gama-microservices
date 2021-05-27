package com.thesis.gamamicroservices.reviewservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.reviewservice.dto.messages.ProductCreatedMessage;
import com.thesis.gamamicroservices.reviewservice.dto.messages.ProductDeletedMessage;
import com.thesis.gamamicroservices.reviewservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="productsReviewServiceQueue")
public class ProductOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductOpsReceiver.class);

    private static final String PRODUCT_CREATED_LOG = "Product created event. Product: {}";
    private static final String PRODUCT_DELETED_LOG = "Product deleted event. Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;

    @RabbitHandler
    public void productDeleted(ProductDeletedMessage productDeleted) {
        logger.info(PRODUCT_DELETED_LOG, productDeleted.getId());
        eventsService.productDeleted(productDeleted.getId());
    }

    @RabbitHandler
    public void productCreated(ProductCreatedMessage productCreated) {
    }



}
