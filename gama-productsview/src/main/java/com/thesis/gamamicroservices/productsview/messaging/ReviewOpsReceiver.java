package com.thesis.gamamicroservices.productsview.messaging;

import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewDeletedMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.ReviewEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="reviewsProductsViewQueue")
public class ReviewOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(ReviewOpsReceiver.class);

    private static final String REVIEW_CREATED_LOG = "Review created event. Review: {}";
    private static final String REVIEW_DELETED_LOG = "Review deleted event. Review: {}";

    @Autowired
    ReviewEventsService reviewEventsService;

    @RabbitHandler
    public void reviewCreated(ReviewCreatedMessage reviewCreated) {
        reviewEventsService.createReview(reviewCreated);
        logger.info(REVIEW_CREATED_LOG, reviewCreated.getReviewId());
    }

    @RabbitHandler
    public void reviewDeleted(ReviewDeletedMessage reviewDeleted) {
        reviewEventsService.deleteReview(reviewDeleted);
        logger.info(REVIEW_DELETED_LOG, reviewDeleted.getReviewId());
    }
}
