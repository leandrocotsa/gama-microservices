package com.thesis.gamamicroservices.inventoryservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.ProductCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.ProductDeletedMessage;
import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.inventoryservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="productsInventoryServiceQueue")
public class ProductOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductOpsReceiver.class);

    private static final String PRODUCT_CREATED_LOG = "Product created event. Product: {}";
    private static final String PRODUCT_DELETED_LOG = "Product deleted event. Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    // lets a single listener invoke different methods, based on the payload type of the incoming message (function arguments)
    //queria filtrar por routing key, neste momento tá tudo product.* e queria método para product.x e product.y
    @RabbitHandler
    public void productCreated(ProductCreatedMessage productCreated) {
        //ProductReplica product = objectMapper.readValue(productJSON, ProductReplica.class);
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
