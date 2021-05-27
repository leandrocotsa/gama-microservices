package com.thesis.gamamicroservices.reviewservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {

    @Bean(name="productsExchange")
    public FanoutExchange productsPubSubExchange() {
        return new FanoutExchange("productExchange");
    }

    @Bean(name="usersExchange")
    public FanoutExchange usersPubSubExchange() {
        return new FanoutExchange("usersExchange");
    }


    @Bean
    public Queue productsQueue() {
        return new Queue("productsReviewServiceQueue");
    }

    @Bean
    public Queue usersQueue() {
        return new Queue("usersReviewServiceQueue");
    }


    @Bean
    public Binding bindingProducts(@Qualifier("productsExchange") FanoutExchange productsExchange) {
        return BindingBuilder
                .bind(productsQueue())
                .to(productsExchange);
    }

    @Bean
    public Binding bindingUsers(@Qualifier("usersExchange") FanoutExchange usersExchange) {
        return BindingBuilder
                .bind(productsQueue())
                .to(usersExchange);
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

