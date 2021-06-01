package com.thesis.gamamicroservices.reviewservice.messaging;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.reviewservice.dto.messages.UserCreatedMessage;
import com.thesis.gamamicroservices.reviewservice.dto.messages.UserDeletedMessage;
import com.thesis.gamamicroservices.reviewservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="usersReviewServiceQueue")
public class UserOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(UserOpsReceiver.class);

    private static final String USER_CREATED_LOG = "User created event. User: {}";
    private static final String USER_DELETED_LOG = "User deleted event. User: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;

    @RabbitHandler
    public void userDeleted(UserDeletedMessage userDeletedMessage) {
        logger.info(USER_DELETED_LOG, userDeletedMessage.getUserId());
        eventsService.userDeleted(userDeletedMessage);
    }

    @RabbitHandler
    public void userCreated(UserCreatedMessage userCreatedMessage) {
    }

}
