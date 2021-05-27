package com.thesis.gamamicroservices.orderservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.orderservice.dto.messages.ProductUpdatedMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.PromotionPriceMessage;
import com.thesis.gamamicroservices.orderservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

@RabbitListener(queues="productsUOrderServiceQueue")
public class ProductUOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ProductCDOpsReceiver.class);

    private static final String PROMOTION_STARTED_LOG = "Promotion started event received, Products: {} changed their promotional price";
    private static final String PROMOTION_ENDED_LOG = "Promotion ended event received, Products: {} changed their promotional price";
    private static final String PRODUCT_PRICE_UPDATED_LOG = "Product price updated event received, Product: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    // lets a single listener invoke different methods, based on the payload type of the incoming message (function arguments)
    //queria filtrar por routing key, neste momento tá tudo product.* e queria método para product.x e product.y
    @RabbitHandler
    public void promotionStarted(String promotionPriceMessageJson) {
        try {
            PromotionPriceMessage p = objectMapper.readValue(promotionPriceMessageJson, PromotionPriceMessage.class);
            logger.info(PROMOTION_STARTED_LOG, p.getProductsIds_and_prices());
            eventsService.promotionStarted(p);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void promotionEnded(ArrayList<Integer> productsEnded) {
        logger.info(PROMOTION_ENDED_LOG, productsEnded);
        eventsService.promotionEnded(productsEnded);
    }

    @RabbitHandler
    public void priceUpdated(ProductUpdatedMessage productUpdated) {
        if(productUpdated.getUpdates().containsKey("price")) {
            Double price = (Double)productUpdated.getUpdates().get("price");
            int productId = (Integer)productUpdated.getUpdates().get("id");
            logger.info(PRODUCT_PRICE_UPDATED_LOG, productId);
            eventsService.priceUpdated(productId, price);
        }
    }

    //este evento dont care aqui, so na view
    @RabbitHandler
    public void productDetailsUpdated(HashMap<Integer,Double> newPrice) { }



}
