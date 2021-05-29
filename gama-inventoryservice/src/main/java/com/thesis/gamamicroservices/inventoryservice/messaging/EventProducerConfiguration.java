package com.thesis.gamamicroservices.inventoryservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.inventoryservice.dto.messages.*;
import org.springframework.amqp.core.DirectExchange;
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


    @Bean(name="stockCheckExchange")
    public DirectExchange stockCheckExchange() {
        return new DirectExchange("stockCheckExchange");
    }

    @Bean(name="inventoryWarehouseExchange")
    public DirectExchange inventoryWarehouseExchange() {
        return new DirectExchange("inventoryWarehouseExchange");
    }

    @Bean
    public ObjectWriter objectWriter(){
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }


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
        idClassMapping.put("order_created", OrderCreatedMessage.class);
        idClassMapping.put("stock_checked", StockCheckMessage.class);
        idClassMapping.put("inventory_updated", StockCheckMessage.class);
        idClassMapping.put("warehouse_created", WarehouseCreatedMessage.class);
        idClassMapping.put("warehouse_deleted", WarehouseDeletedMessage.class);
        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }
}
