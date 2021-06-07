package com.thesis.gamamicroservices.userservice.messaging;

import com.thesis.gamamicroservices.userservice.dto.messages.*;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
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

    @Bean(name="userExchange")
    public Exchange userExchange() {
        return new FanoutExchange("userExchange");
    }

    @Bean(name="userUExchange")
    public Exchange userUExchange() {
        return new DirectExchange("userUExchange");
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
        idClassMapping.put("user_created", UserCreatedMessage.class);
        idClassMapping.put("user_deleted", UserDeletedMessage.class);
        idClassMapping.put("user_updated", UserUpdatedMessage.class);
        idClassMapping.put("address_created", AddressCreatedMessage.class);
        idClassMapping.put("address_deleted", AddressDeletedMessage.class);
        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }


}
