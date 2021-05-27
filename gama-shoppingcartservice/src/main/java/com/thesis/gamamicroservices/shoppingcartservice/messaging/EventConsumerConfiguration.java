package com.thesis.gamamicroservices.shoppingcartservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {

    @Bean(name="userExchange")
    public FanoutExchange userExchange() {
        return new FanoutExchange("userExchange");
    }

    @Bean
    public Queue usersQueue() {
        return new Queue("usersShoppingCartQueue");
    }



    @Bean
    public Binding bindingUsers(FanoutExchange userExchange) {
        return BindingBuilder
                .bind(usersQueue())
                .to(userExchange);
    }


    @Bean
    public UserOpsReceiver userOpsReceiver() {
        return new UserOpsReceiver();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
    }



}

