package com.thesis.gamamicroservices.productsview.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.ProductCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.ProductDeletedMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.ProductsEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="productsCDProductsViewQueue")
public class ProductCDOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String PRODUCT_CREATED_LOG = "Product created event. Product: {}";
    private static final String PRODUCT_DELETED_LOG = "Product deleted event. Product: {}";

    @Autowired
    ProductsEventsService productsEventsService;

    @RabbitHandler
    public void productCreated(ProductCreatedMessage productCreated) {
        //ProductReplica product = new ProductReplica(productCreated);
        logger.info(PRODUCT_CREATED_LOG, productCreated.getId());
        productsEventsService.productCreated(productCreated);
    }

    @RabbitHandler
    public void productDeleted(ProductDeletedMessage productDeleted) {
        logger.info(PRODUCT_DELETED_LOG, productDeleted.getId());
        productsEventsService.productDeleted(productDeleted);
    }



}
