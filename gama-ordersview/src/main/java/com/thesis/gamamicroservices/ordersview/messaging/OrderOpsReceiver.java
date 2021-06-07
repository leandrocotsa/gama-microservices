package com.thesis.gamamicroservices.ordersview.messaging;

import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderConfirmedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderCreatedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderStatusUpdateMessage;
import com.thesis.gamamicroservices.ordersview.messaging.service.OrderEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="ordersOrdersViewQueue")
public class OrderOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(OrderOpsReceiver.class);

    private static final String ORDER_CREATED_LOG = "Order created event received. Checking stock for Order: {}";
    private static final String ORDER_CONFIRMED_LOG = "Order confirmed event received. Order: {}";
    private static final String ORDER_STATUS_UPDATED_LOG = "Order status update event received. Order: {}";

    @Autowired
    OrderEventsService orderEventsService;

    @RabbitHandler
    public void orderCreated(OrderCreatedMessage orderCreated) {
        orderEventsService.createOrder(orderCreated);
        logger.info(ORDER_CREATED_LOG, orderCreated.getOrderId());
    }

    @RabbitHandler
    public void orderConfirmed(OrderConfirmedMessage orderConfirmed) {
        orderEventsService.confirmOrder(orderConfirmed);
        logger.info(ORDER_CONFIRMED_LOG, orderConfirmed.getOrderId());
    }


    @RabbitHandler
    public void orderStatusUpdated(OrderStatusUpdateMessage orderStatusUpdateMessage) {
        orderEventsService.updateOrderStatus(orderStatusUpdateMessage);
        logger.info(ORDER_STATUS_UPDATED_LOG, orderStatusUpdateMessage.getOrderId());
    }



}
