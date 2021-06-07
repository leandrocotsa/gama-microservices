package com.thesis.gamamicroservices.productsview.messaging;

import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionUpdatedMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.PromotionEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="promotionCUDProductsViewQueue")
public class PromotionOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(PromotionOpsReceiver.class);

    private static final String PROMOTION_CREATED_LOG = "Promotion created event. Promotion: {}";
    private static final String PROMOTION_DELETED_LOG = "Promotion deleted event. Promotion: {}";
    private static final String PROMOTION_UPDATED_LOG = "Promotion updated event. Promotion: {}";

    @Autowired
    PromotionEventsService promotionEventsService;

    @RabbitHandler
    public void promotionCreated(PromotionCreatedMessage promotionCreated) {
        promotionEventsService.createPromotion(promotionCreated);
        logger.info(PROMOTION_CREATED_LOG, promotionCreated.getPromotionId());
    }

    @RabbitHandler
    public void promotionDeleted(PromotionDeletedMessage promotionDeleted) {
        promotionEventsService.deletePromotion(promotionDeleted);
        logger.info(PROMOTION_DELETED_LOG, promotionDeleted.getPromotionId());
    }

    @RabbitHandler
    public void promotionUpdated(PromotionUpdatedMessage promotionUpdated) {
        promotionEventsService.updatePromotion(promotionUpdated);
        logger.info(PROMOTION_UPDATED_LOG, promotionUpdated.getPromotionId());
    }


}
