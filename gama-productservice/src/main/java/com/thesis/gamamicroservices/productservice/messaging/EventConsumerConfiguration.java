package com.thesis.gamamicroservices.productservice.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {

    @Bean(name="promotionExchange")
    public FanoutExchange promotionExchange() {
        return new FanoutExchange("promotionExchange");
    }

    @Bean
    public Queue promotionProductServiceQueue() {
        return new Queue("promotionProductServiceQueue");
    }



    @Bean
    public Binding bindingUsers(FanoutExchange promotionExchange) {
        return BindingBuilder
                .bind(promotionProductServiceQueue())
                .to(promotionExchange);
    }


    @Bean
    public PromotionOpsReceiver promotionOpsReceiver() {
        return new PromotionOpsReceiver();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
    }



}

