package com.thesis.gamamicroservices.orderservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.orderservice.dto.messages.*;
import com.thesis.gamamicroservices.orderservice.service.OrderService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventProducerConfiguration {

    @Bean
    public ObjectWriter objectWriter(){
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @Bean(name="ordersExchange")
    public Exchange ordersExchange() {
        return new TopicExchange("ordersExchange");
    }

    /*
    @Bean(name="orderPriceExchange")
    public DirectExchange orderPriceExchange() {
        return new DirectExchange("orderPriceExchange");
    }
     */


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper classMapper = new DefaultJackson2JavaTypeMapper();
        classMapper.setTrustedPackages("*");
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("product_created", ProductCreatedMessage.class);
        idClassMapping.put("product_deleted", ProductDeletedMessage.class);
        idClassMapping.put("product_updated", ProductUpdatedMessage.class);
        idClassMapping.put("order_created", OrderCreatedMessage.class);
        idClassMapping.put("order_confirmed", OrderConfirmedMessage.class);
        idClassMapping.put("order_updated", OrderStatusUpdateMessage.class);
        idClassMapping.put("stock_checked", StockCheckMessage.class);
        idClassMapping.put("promotion_price_start", PromotionPriceMessage.class);
        idClassMapping.put("promotion_price_reset", PromotionPriceResetMessage.class);
        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }
}
