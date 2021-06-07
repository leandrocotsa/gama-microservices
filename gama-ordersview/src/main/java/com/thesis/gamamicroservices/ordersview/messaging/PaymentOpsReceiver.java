package com.thesis.gamamicroservices.ordersview.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.ordersview.dto.messages.payment_service.PaymentCreatedMessage;
import com.thesis.gamamicroservices.ordersview.messaging.service.PaymentEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="paymentConfirmedOrdersViewQueue")
public class PaymentOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(PaymentOpsReceiver.class);

    private static final String PAYMENT_CONFIRMED_LOG = "Payment confirmed event. Order: {}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PaymentEventsService paymentEventsService;

    @RabbitHandler
    public void paymentConfirmed(PaymentCreatedMessage paymentCreatedMessage) {
        logger.info(PAYMENT_CONFIRMED_LOG, paymentCreatedMessage.getOrderId());
        paymentEventsService.paymentConfirmed(paymentCreatedMessage.getOrderId());
    }

}
