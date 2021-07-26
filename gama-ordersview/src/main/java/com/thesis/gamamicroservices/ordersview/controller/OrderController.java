package com.thesis.gamamicroservices.ordersview.controller;

import com.thesis.gamamicroservices.ordersview.model.Order;
import com.thesis.gamamicroservices.ordersview.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllProducts() {
        return ResponseEntity.ok(this.orderService.findAllOrders());
    }

}
