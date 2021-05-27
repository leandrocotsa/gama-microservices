package com.thesis.gamamicroservices.productservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.productservice.dto.PromotionStartedMessageDTO;
import com.thesis.gamamicroservices.productservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


@RabbitListener(queues="promotionProductServiceQueue")
public class PromotionOpsReceiver {

    private static final Logger logger = LoggerFactory.getLogger(PromotionOpsReceiver.class);

    private static final String PROMOTION_STARTED_LOG = "Promotion started event received, Products: {} - changed their promotional price";
    private static final String PROMOTION_ENDED_LOG = "Promotion ended event received, Products: {} - changed their promotional price";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    @RabbitHandler
    public void promotionStarted(String promotionStartedJson) {
        try {
            PromotionStartedMessageDTO p = objectMapper.readValue(promotionStartedJson, PromotionStartedMessageDTO.class);
            eventsService.promotionStarted(p);
            logger.info(PROMOTION_STARTED_LOG, p.getProductsIds());
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void promotionEnded(ArrayList<Integer> productsEnded) {
        eventsService.promotionEnded(productsEnded);
        logger.info(PROMOTION_ENDED_LOG, productsEnded);
    }

/**
    @RabbitHandler
    public void productRemovedFromActivePromotion(Integer productId) {

        logger.info("Product removed from active promotion event.");
        eventsService.productRemovedFromActivePromotion(productId);
    }


**/

}
