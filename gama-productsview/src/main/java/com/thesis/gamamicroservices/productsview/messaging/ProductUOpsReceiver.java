package com.thesis.gamamicroservices.productsview.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.ProductUpdatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.PromotionPriceMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.PromotionPriceResetMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.ProductsEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="productsUProductsViewQueue")
public class ProductUOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String PROMOTION_STARTED_LOG = "Promotion started event received, Products: {} changed their promotional price";
    private static final String PROMOTION_ENDED_LOG = "Promotion ended event received, Products: {} changed their promotional price";
    private static final String PRODUCT_UPDATED_LOG = "Product updated event received, Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductsEventsService productsEventsService;

    @RabbitHandler
    public void promotionStarted(PromotionPriceMessage promotionPriceMessage) {
        logger.info(PROMOTION_STARTED_LOG, promotionPriceMessage.getProductsIds_and_prices());
        productsEventsService.promotionStarted(promotionPriceMessage);
    }

    @RabbitHandler
    public void promotionEnded(PromotionPriceResetMessage promotionPriceResetMessage) {
        logger.info(PROMOTION_ENDED_LOG, promotionPriceResetMessage.getProductsEnded());
        productsEventsService.promotionEnded(promotionPriceResetMessage);
    }

    @RabbitHandler
    public void productUpdated(ProductUpdatedMessage productUpdated) {
        logger.info(PRODUCT_UPDATED_LOG, productUpdated.getUpdates().get("id"));
        productsEventsService.editProduct(productUpdated);
    }



}
