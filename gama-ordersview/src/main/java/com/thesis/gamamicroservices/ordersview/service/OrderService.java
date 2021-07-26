package com.thesis.gamamicroservices.ordersview.service;

import com.thesis.gamamicroservices.ordersview.model.Order;
import com.thesis.gamamicroservices.ordersview.model.User;
import com.thesis.gamamicroservices.ordersview.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    private static final String PRODUCT_NOT_FOUND = "There is no product with id: ";
/**
    public Product getProductById(int id) throws NoDataFoundException {
        return productRepository.findByProductId(id)
                .orElseThrow(() -> new NoDataFoundException(PRODUCT_NOT_FOUND + id));
    }
 **/

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
/**
    public List<Order> getUserOrders(String authorizationToken) {
        List<Order> userOrders = new ArrayList<>();
        //String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        User user = this.userService.getMyUser(authorizationToken);
        this.orderRepository.findByUserOrderByBuyDate(user).forEach(order -> userOrders.add(new OrderGetDTO(order)));
        return userOrders;
    }
 **/

}
