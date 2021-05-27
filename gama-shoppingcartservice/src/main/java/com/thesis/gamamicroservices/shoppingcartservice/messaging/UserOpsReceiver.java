package com.thesis.gamamicroservices.shoppingcartservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.shoppingcartservice.dto.UserCreatedDTO;
import com.thesis.gamamicroservices.shoppingcartservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


@RabbitListener(queues="usersShoppingCartQueue")
public class UserOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(UserOpsReceiver.class);

    private static final String USER_CREATED_LOG = "User created event. User: {}";
    private static final String USER_DELETED_LOG = "User deleted event. User: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    @RabbitHandler
    public void userCreated(String userJSON) {
        try {
            UserCreatedDTO user = objectMapper.readValue(userJSON, UserCreatedDTO.class);
            logger.info(USER_CREATED_LOG, user.getId());
            eventsService.userCreated(user);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void userDeleted(Integer userId) {
        logger.info(USER_DELETED_LOG, userId);
        eventsService.userDeleted(userId);
    }



}
