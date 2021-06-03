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

import java.util.HashMap;

@RabbitListener(queues="productsUProductsViewQueue")
public class ProductUOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String PROMOTION_STARTED_LOG = "Promotion started event received, Products: {} changed their promotional price";
    private static final String PROMOTION_ENDED_LOG = "Promotion ended event received, Products: {} changed their promotional price";
    private static final String PRODUCT_PRICE_UPDATED_LOG = "Product price updated event received, Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductsEventsService eventsService;


    // lets a single listener invoke different methods, based on the payload type of the incoming message (function arguments)
    //queria filtrar por routing key, neste momento tá tudo product.* e queria método para product.x e product.y
    @RabbitHandler
    public void promotionStarted(PromotionPriceMessage promotionPriceMessage) {
        //eventsService.promotionStarted(promotionPriceMessage);
    }

    @RabbitHandler
    public void promotionEnded(PromotionPriceResetMessage promotionPriceResetMessage) {
        logger.info(PROMOTION_ENDED_LOG, promotionPriceResetMessage.getProductsEnded());
        //eventsService.promotionEnded(promotionPriceResetMessage);
    }

    @RabbitHandler
    public void priceUpdated(ProductUpdatedMessage productUpdated) {
        if(productUpdated.getUpdates().containsKey("price")) {
            Double price = (Double)productUpdated.getUpdates().get("price");
            int productId = (Integer)productUpdated.getUpdates().get("id");
            if(productUpdated.getUpdates().containsKey("promotionPrice")) {
                Double promotionPrice = (Double)productUpdated.getUpdates().get("promotionPrice");
                logger.info(PRODUCT_PRICE_UPDATED_LOG, productId);
                //eventsService.priceUpdated(productId, price, promotionPrice);
            } else {
                logger.info(PRODUCT_PRICE_UPDATED_LOG, productId);
                //eventsService.priceUpdated(productId, price);
            }

        }
    }



}
