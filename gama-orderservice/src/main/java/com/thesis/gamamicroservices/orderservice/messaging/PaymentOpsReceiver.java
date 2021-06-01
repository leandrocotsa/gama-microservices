package com.thesis.gamamicroservices.orderservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.orderservice.dto.messages.consumed.PaymentCreatedMessage;
import com.thesis.gamamicroservices.orderservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="paymentOrderServiceQueue")
public class PaymentOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(PaymentOpsReceiver.class);

    private static final String PAYMENT_CONFIRMED_LOG = "Payment confirmed event. Order: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;

    @RabbitHandler
    public void paymentConfirmed(PaymentCreatedMessage paymentCreatedMessage) {
        logger.info(PAYMENT_CONFIRMED_LOG, paymentCreatedMessage.getOrderId());
        eventsService.paymentConfirmed(paymentCreatedMessage.getOrderId());
    }

}
