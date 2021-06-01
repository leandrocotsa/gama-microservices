package com.thesis.gamamicroservices.orderservice.messaging;

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

    @Bean(name="productUpdatedExchange")
    public FanoutExchange productUpdatedExchange() {
        return new FanoutExchange("productUpdatedExchange");
    }

    @Bean(name= "stockCheckExchange")
    public DirectExchange stockCheckExchange() {
        return new DirectExchange("stockCheckExchange");
    }

    @Bean(name="paymentConfirmedExchange")
    public FanoutExchange paymentConfirmedExchange() {
        return new FanoutExchange("paymentConfirmedExchange");
    }

    @Bean
    public Queue productsCreatedDeletedQueue() {
        return new Queue("productsCDOrderServiceQueue");
    }

    @Bean
    public Queue productsUpdatedQueue() {
        return new Queue("productsUOrderServiceQueue");
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue("stockInventoryServiceQueue");
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue("paymentOrderServiceQueue");
    }



    @Bean
    public Binding bindingProductsCreatedDeleted(@Qualifier("productCreatedDeletedExchange") FanoutExchange productCreatedDeletedExchange) {
        return BindingBuilder
                .bind(productsCreatedDeletedQueue())
                .to(productCreatedDeletedExchange);
    }

    @Bean
    public Binding bindingProductsUpdated(@Qualifier("productUpdatedExchange") FanoutExchange productUpdatedExchange) {
        return BindingBuilder
                .bind(productsUpdatedQueue())
                .to(productUpdatedExchange);
    }


    @Bean
    public Binding bindingInventory(@Qualifier("stockCheckExchange") DirectExchange stockExchange) {
        return BindingBuilder
                .bind(inventoryQueue())
                .to(stockExchange)
                .with("stock");
    }

    @Bean
    public Binding bindingPaymentConfirmed(@Qualifier("paymentConfirmedExchange") FanoutExchange paymentExchange) {
        return BindingBuilder
                .bind(paymentQueue())
                .to(paymentExchange);
    }


    @Bean
    public ProductCDOpsReceiver productCDReceiver() {
        return new ProductCDOpsReceiver();
    }

    @Bean
    public ProductUOpsReceiver productUReceiver() {
        return new ProductUOpsReceiver();
    }

    @Bean
    public InventoryOpsReceiver inventoryEventsReceiver() {
        return new InventoryOpsReceiver();
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



}

