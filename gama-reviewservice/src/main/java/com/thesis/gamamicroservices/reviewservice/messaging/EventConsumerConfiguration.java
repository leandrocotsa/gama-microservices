package com.thesis.gamamicroservices.reviewservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {

    @Bean(name="productCreatedDeletedExchange")
    public FanoutExchange productCreatedDeletedExchange() {
        return new FanoutExchange("productCreatedDeletedExchange");
    }

    @Bean(name="userExchange")
    public FanoutExchange userExchange() {
        return new FanoutExchange("userExchange");
    }

    @Bean
    public Queue usersQueue() {
        return new Queue("usersReviewServiceQueue");
    }

    @Bean
    public Queue productsCreatedDeletedQueue() {
        return new Queue("productsReviewServiceQueue");
    }

    @Bean
    public Binding bindingUsers(@Qualifier("userExchange")FanoutExchange userExchange) {
        return BindingBuilder
                .bind(usersQueue())
                .to(userExchange);
    }

    @Bean
    public Binding bindingProducts(@Qualifier("productCreatedDeletedExchange") FanoutExchange productsExchange) {
        return BindingBuilder
                .bind(productsCreatedDeletedQueue())
                .to(productsExchange);
    }


    @Bean
    public ProductOpsReceiver productEventsReceiver() {
        return new ProductOpsReceiver();
    }

    @Bean
    public UserOpsReceiver userEventsReceiver() {
        return new UserOpsReceiver();
    }


    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
    }

}

