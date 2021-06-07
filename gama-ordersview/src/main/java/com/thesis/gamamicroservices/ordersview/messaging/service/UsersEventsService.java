package com.thesis.gamamicroservices.ordersview.messaging.service;

import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.*;
import org.springframework.stereotype.Service;

@Service
public class UsersEventsService {

    public void userCreated(UserCreatedMessage userCreatedMessage) {
    }

    public void userDeleted(UserDeletedMessage userDeletedMessage) {
    }

    public void userUpdated(UserUpdatedMessage userUpdatedMessage) {
    }

    public void addressAdded(AddressCreatedMessage addressCreatedMessage) {
    }

    public void addressRemoved(AddressDeletedMessage addressDeletedMessage) {
    }
}
