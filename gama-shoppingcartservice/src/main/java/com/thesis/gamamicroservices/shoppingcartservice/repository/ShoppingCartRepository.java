package com.thesis.gamamicroservices.shoppingcartservice.repository;

import com.thesis.gamamicroservices.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    ShoppingCart findByUserId(int userId);
}
