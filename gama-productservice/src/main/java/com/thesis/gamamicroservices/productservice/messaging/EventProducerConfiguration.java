package com.thesis.gamamicroservices.productservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.productservice.dto.messages.consumed.PromotionEndedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.consumed.PromotionStartedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.produced.*;
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

    @Bean(name="productCreatedDeletedExchange")
    public Exchange productCreatedDeletedExchange() {
        return new FanoutExchange("productCreatedDeletedExchange");
    }

    @Bean(name="productUpdatedExchange")
    public Exchange productUpdatedExchange() {
        return new FanoutExchange("productUpdatedExchange");
    }

    @Bean(name="brandsCategoriesExchange")
    public Exchange brandsCategoriesExchange() {
        return new DirectExchange("brandsCategoriesExchange");
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
        idClassMapping.put("product_updated", ProductUpdatedMessage.class);
        idClassMapping.put("brand_created", BrandCreatedMessage.class);
        idClassMapping.put("brand_deleted", BrandDeletedMessage.class);
        idClassMapping.put("category_created", CategoryCreatedMessage.class);
        idClassMapping.put("category_deleted", CategoryDeletedMessage.class);
        idClassMapping.put("promotion_started", PromotionStartedMessage.class);
        idClassMapping.put("promotion_ended", PromotionEndedMessage.class);
        idClassMapping.put("promotion_price_start", PromotionPriceMessage.class);
        idClassMapping.put("promotion_price_reset", PromotionPriceResetMessage.class);
        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }

}
