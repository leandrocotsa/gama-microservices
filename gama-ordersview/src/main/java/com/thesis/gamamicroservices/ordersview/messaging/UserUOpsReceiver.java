package com.thesis.gamamicroservices.ordersview.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.*;
import com.thesis.gamamicroservices.ordersview.messaging.service.UsersEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="userUOrdersViewQueue")
public class UserUOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(UserUOpsReceiver.class);

    private static final String USER_UPDATED_LOG = "User updated event. User: {}";
    private static final String ADDRESS_ADDED_LOG = "Address added event. User: {}";
    private static final String ADDRESS_REMOVED_LOG = "Address removed event. User: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsersEventsService usersEventsService;


    @RabbitHandler
    public void userUpdated(UserUpdatedMessage userUpdatedMessage) {
        logger.info(USER_UPDATED_LOG, userUpdatedMessage.getUserId());
        usersEventsService.userUpdated(userUpdatedMessage);
    }

    @RabbitHandler
    public void addressAdded(AddressCreatedMessage addressCreatedMessage) {
        logger.info(ADDRESS_ADDED_LOG, addressCreatedMessage.getUserId());
        usersEventsService.addressAdded(addressCreatedMessage);
    }

    @RabbitHandler
    public void addressRemoved(AddressDeletedMessage addressDeletedMessage) {
        logger.info(ADDRESS_ADDED_LOG, addressDeletedMessage.getUserId());
        usersEventsService.addressRemoved(addressDeletedMessage);
    }

}
