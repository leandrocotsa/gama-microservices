package com.thesis.gamamicroservices.paymentservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {


    @Bean
    public DirectExchange orderPriceExchange() {
        return new DirectExchange("orderPriceExchange");
    }

    @Bean
    public Queue orderPriceQueue() {
        return new Queue("orderPaymentServiceQueue");
    }

    @Bean
    public Binding bindingOrderPrice(DirectExchange orderPriceExchange) {
        return BindingBuilder
                .bind(orderPriceQueue())
                .to(orderPriceExchange)
                .with("order");
    }

    @Bean
    public OrderConfirmedOpReceiver inventoryEventsReceiver() {
        return new OrderConfirmedOpReceiver();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
    }



}

