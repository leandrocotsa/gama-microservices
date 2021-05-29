package com.thesis.gamamicroservices.shoppingcartservice.service;

import com.thesis.gamamicroservices.shoppingcartservice.dto.UserCreatedDTO;
import com.thesis.gamamicroservices.shoppingcartservice.dto.messages.UserCreatedMessage;
import com.thesis.gamamicroservices.shoppingcartservice.dto.messages.UserDeletedMessage;
import com.thesis.gamamicroservices.shoppingcartservice.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public EventsService(ShoppingCartRepository shoppingCartRepository, ShoppingCartService shoppingCartService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public void userCreated(UserCreatedMessage user) {
        shoppingCartService.createShoppingCartForUser(user.getId());
    }

    public void userDeleted(UserDeletedMessage userDeletedMessage) {
        shoppingCartRepository.deleteById(userDeletedMessage.getUserId());
    }


}
