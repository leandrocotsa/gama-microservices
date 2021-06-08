package com.thesis.gamamicroservices.ordersview.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderConfirmedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderCreatedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderStatusUpdateMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.payment_service.PaymentCreatedMessage;
import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EventConsumerConfiguration {

    //---------ORDER-SERVICE----------

    @Bean(name="ordersExchange")
    public TopicExchange ordersExchange() {
        return new TopicExchange("ordersExchange");
    }

    @Bean
    public Queue ordersOrdersViewQueue() {
        return new Queue("ordersOrdersViewQueue");
    }

    @Bean
    public Binding bindingOrders(@Qualifier("ordersExchange") TopicExchange ordersExchange) {
        return BindingBuilder
                .bind(ordersOrdersViewQueue())
                .to(ordersExchange)
                .with("order.*");
    }

    //---------USER-SERVICE----------

    @Bean(name="userExchange")
    public FanoutExchange userExchange() {
        return new FanoutExchange("userExchange");
    }

    @Bean(name="userUExchange")
    public DirectExchange userUExchange() {
        return new DirectExchange("userUExchange");
    }

    @Bean
    public Queue userOrdersViewQueue() {
        return new Queue("userOrdersViewQueue");
    }

    @Bean
    public Queue userUOrdersViewQueue() {
        return new Queue("userUOrdersViewQueue");
    }

    @Bean
    public Binding bindingUsers(@Qualifier("userExchange") FanoutExchange userExchange) {
        return BindingBuilder
                .bind(userOrdersViewQueue())
                .to(userExchange);
    }

    @Bean
    public Binding bindingUUsers(@Qualifier("userUExchange") DirectExchange userUExchange) {
        return BindingBuilder
                .bind(userUOrdersViewQueue())
                .to(userUExchange)
                .with("users");
    }


    //---------PAYMENT-SERVICE----------

    @Bean(name="paymentConfirmedExchange")
    public FanoutExchange paymentConfirmedExchange() {
        return new FanoutExchange("paymentConfirmedExchange");
    }

    @Bean
    public Queue paymentConfirmedOrdersViewQueue() {
        return new Queue("paymentConfirmedOrdersViewQueue");
    }

    @Bean
    public Binding bindingPaymentConfirmed(@Qualifier("paymentConfirmedExchange") FanoutExchange paymentConfirmedExchange) {
        return BindingBuilder
                .bind(paymentConfirmedOrdersViewQueue())
                .to(paymentConfirmedExchange);
    }


    @Bean
    public OrderOpsReceiver orderOpsReceiver() {
        return new OrderOpsReceiver();
    }

    @Bean
    public UserOpsReceiver userOpsReceiver() {
        return new UserOpsReceiver();
    }

    @Bean
    public UserUOpsReceiver userUOpsReceiver() {
        return new UserUOpsReceiver();
    }

    @Bean
    public PaymentOpsReceiver paymentOpsReceiver() {
        return new PaymentOpsReceiver();
    }



    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return o;
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

        idClassMapping.put("order_created", OrderCreatedMessage.class);
        idClassMapping.put("order_confirmed", OrderConfirmedMessage.class);
        idClassMapping.put("order_updated", OrderStatusUpdateMessage.class);

        idClassMapping.put("payment_confirmed", PaymentCreatedMessage.class);

        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }



}

