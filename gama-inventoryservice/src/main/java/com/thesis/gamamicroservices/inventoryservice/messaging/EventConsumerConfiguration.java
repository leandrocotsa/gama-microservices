package com.thesis.gamamicroservices.inventoryservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {

    @Bean(name="productsExchange")
    public FanoutExchange productsPubSubExchange() {
        return new FanoutExchange("productCreatedDeletedExchange");
    }

    @Bean(name="ordersExchange")
    public TopicExchange ordersExchange() {
        return new TopicExchange("ordersExchange");
    }

    @Bean
    public Queue productsQueue() {
        return new Queue("productsInventoryServiceQueue");
    }

    @Bean
    public Queue ordersQueue() {
        return new Queue("ordersInventoryServiceQueue");
    }

    /**
    @Bean
    public Binding binding(Queue queue, Exchange eventExchange) {
        return BindingBuilder
                .bind(queue)
                .to(eventExchange)
                .with("product.*").noargs();
    }
     **/

    @Bean
    public Binding bindingProducts(@Qualifier("productsExchange") FanoutExchange productsExchange) {
        return BindingBuilder
                .bind(productsQueue())
                .to(productsExchange);
    }

    @Bean
    public Binding bindingOrders(@Qualifier("ordersExchange") TopicExchange eventExchange) {
        return BindingBuilder
                .bind(ordersQueue())
                .to(eventExchange)
                .with("order.created");
    }

/**
    @Bean
    public ProductOpsReceiver eventReceiver() {
        return new ProductOpsReceiver();
    }
**/


    @Bean
    public ProductOpsReceiver productEventsReceiver() {
        return new ProductOpsReceiver();
    }

    @Bean
    public OrderOpsReceiver orderEventsReceiver() {
        return new OrderOpsReceiver();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
    }

}

