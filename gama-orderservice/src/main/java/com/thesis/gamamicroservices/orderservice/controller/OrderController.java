package com.thesis.gamamicroservices.orderservice.controller;

import com.thesis.gamamicroservices.orderservice.dto.OrderSetDTO;
import com.thesis.gamamicroservices.orderservice.dto.ShippingReferenceSetDTO;
import com.thesis.gamamicroservices.orderservice.service.AlreadyExistsException;
import com.thesis.gamamicroservices.orderservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.orderservice.service.OrderService;
import com.thesis.gamamicroservices.orderservice.service.ShippingService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static final String ORDER_CREATED_LOG = "An Order was created";
    private static final String ORDER_DELETED_LOG = "Order: {} was deleted";

    private final OrderService orderService;
    private final ShippingService shippingService;

    @Autowired
    public OrderController(OrderService orderService, ShippingService shippingService) {
        this.orderService = orderService;
        this.shippingService = shippingService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader("Authorization") String authorizationToken, @RequestBody OrderSetDTO orderSetDTO) throws NoDataFoundException {
        this.orderService.createOrder(orderSetDTO, authorizationToken);
        logger.info(ORDER_CREATED_LOG);
    }

    //if for some reason an order has to be deleted
    @DeleteMapping(path="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@RequestHeader("Authorization") String authorizationToken, @PathVariable int id) throws NoDataFoundException {
        this.orderService.deleteOrder(authorizationToken, id);
        logger.info(ORDER_DELETED_LOG, id);
    }

    //provisory
    @PostMapping(path="/shipping")
    @ResponseStatus(HttpStatus.CREATED)
    public void createShippingReference(@RequestBody ShippingReferenceSetDTO shippingReferenceSetDTO) throws NoDataFoundException, AlreadyExistsException {
        this.shippingService.createShippingReference(shippingReferenceSetDTO);
    }



    //cancel order seria ela simplesmente expirar por falta de pagamento


}