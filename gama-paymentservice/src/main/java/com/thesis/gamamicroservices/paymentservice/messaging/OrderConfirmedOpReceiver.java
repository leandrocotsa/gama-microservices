package com.thesis.gamamicroservices.paymentservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.paymentservice.model.foreign.ConfirmedOrderReplica;
import com.thesis.gamamicroservices.paymentservice.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="orderPaymentServiceQueue")
public class OrderConfirmedOpReceiver {

    private Logger logger = LoggerFactory.getLogger(OrderConfirmedOpReceiver.class);

    private static final String ORDER_CONFIRMED_LOG = "Order confirmed event. Order: '{}";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventsService eventsService;


    @RabbitHandler
    public void saveOrderConfirmed(String orderJSON) {
        try {
            ConfirmedOrderReplica order = objectMapper.readValue(orderJSON, ConfirmedOrderReplica.class);
            logger.info(ORDER_CONFIRMED_LOG, order.getId());
            eventsService.saveOrderConfirmed(order);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //as orders que estão payment pending há mais de 24 horas expiram e apaga-se daqui para impedir que seja paga
    //as orders no order service têm de expirar senao o stock fica reservado sempre
    //se por acaso a order tiver expirado e o evento ainda nao tiver sido propagado e alguem pagar nesse intervalo
    //o order service ao receber o evento daqui de order payed overwrites o estado de expired para payed (pode é haver porblemas de stock depois mas é muito raro
    //senao uma pessoa pagava e tava expired
    //no entanto se no payment ja tiver sido recebido o event de order expired entao nao existe hipotese de apagar
    @RabbitHandler
    public void orderExpired(Integer orderId) {
        eventsService.orderExpired(orderId);
    }


}
