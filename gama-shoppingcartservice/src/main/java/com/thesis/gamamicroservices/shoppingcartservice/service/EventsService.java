package com.thesis.gamamicroservices.shoppingcartservice.service;

import com.thesis.gamamicroservices.shoppingcartservice.dto.UserCreatedDTO;
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

    public void userCreated(UserCreatedDTO user) {
        shoppingCartService.createShoppingCartForUser(user.getId());
    }

    public void userDeleted(int userId) {
        shoppingCartRepository.deleteById(userId);
    }


}
