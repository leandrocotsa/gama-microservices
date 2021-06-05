package com.thesis.gamamicroservices.productsview.messaging;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.InventoryUpdatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.inventory_service.WarehouseDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.*;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionUpdatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.review_service.ReviewDeletedMessage;
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

    //---------PRODUCT-SERVICE----------

    @Bean(name="productCreatedDeletedExchange")
    public FanoutExchange productCreatedDeletedExchange() {
        return new FanoutExchange("productCreatedDeletedExchange");
    }

    @Bean(name="productUpdatedExchange")
    public FanoutExchange productUpdatedExchange() {
        return new FanoutExchange("productUpdatedExchange");
    }

    @Bean(name="brandsCategoriesExchange")
    public DirectExchange brandsCategoriesExchange() {
        return new DirectExchange("brandsCategoriesExchange");
    }


    @Bean
    public Queue productsCreatedDeletedQueue() {
        return new Queue("productsCDProductsViewQueue");
    }

    @Bean
    public Queue productsUpdatedQueue() {
        return new Queue("productsUProductsViewQueue");
    }

    @Bean
    public Queue brandsCategoriesQueue() {
        return new Queue("brandsCategoriesProductsViewQueue");
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
    public Binding bindingBrandsCategories(@Qualifier("brandsCategoriesExchange") DirectExchange brandsCategoriesExchange) {
        return BindingBuilder
                .bind(brandsCategoriesQueue())
                .to(brandsCategoriesExchange)
                .with("brandsCategories");
    }

    //---------REVIEW-SERVICE----------

    @Bean(name="reviewExchange")
    public DirectExchange reviewExchange() {
        return new DirectExchange("reviewExchange");
    }

    @Bean
    public Queue reviewsProductsViewQueue() {
        return new Queue("reviewsProductsViewQueue");
    }

    @Bean
    public Binding bindingReviews(@Qualifier("reviewExchange") DirectExchange reviewExchange) {
        return BindingBuilder
                .bind(reviewsProductsViewQueue())
                .to(reviewExchange)
                .with("review");
    }

    //---------INVENTORY-SERVICE----------

    @Bean(name="inventoryWarehouseExchange")
    public DirectExchange inventoryWarehouseExchange() {
        return new DirectExchange("inventoryWarehouseExchange");
    }

    @Bean
    public Queue inventoryWarehouseProductsViewQueue() {
        return new Queue("inventoryWarehouseProductsViewQueue");
    }

    @Bean
    public Binding bindingInventoryWarehouse(@Qualifier("inventoryWarehouseExchange") DirectExchange inventoryWarehouseExchange) {
        return BindingBuilder
                .bind(inventoryWarehouseProductsViewQueue())
                .to(inventoryWarehouseExchange)
                .with("inventory");
    }

    //---------PROMOTION-SERVICE----------

    @Bean(name="promotionCUDExchange")
    public DirectExchange promotionCUDExchange() {
        return new DirectExchange("promotionCUDExchange");
    }

    @Bean
    public Queue promotionCUDProductsViewQueue() {
        return new Queue("promotionCUDProductsViewQueue");
    }

    @Bean
    public Binding bindingPromotion(@Qualifier("promotionCUDExchange") DirectExchange promotionCUDExchange) {
        return BindingBuilder
                .bind(promotionCUDProductsViewQueue())
                .to(promotionCUDExchange)
                .with("promotion");
    }

    @Bean
    public ProductCDOpsReceiver productCDReceiver() {
        return new ProductCDOpsReceiver();
    }

    @Bean
    public InventoryOpsReceiver inventoryOpsReceiver() {
        return new InventoryOpsReceiver();
    }

    @Bean
    public ReviewOpsReceiver reviewOpsReceiver() {
        return new ReviewOpsReceiver();
    }

    @Bean
    public BrandCategoryOpsReceiver brandCategoryOpsReceiver() {
        return new BrandCategoryOpsReceiver();
    }

    @Bean
    public ProductUOpsReceiver productUOpsReceiver() {
        return new ProductUOpsReceiver();
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
        idClassMapping.put("product_created", ProductCreatedMessage.class);
        idClassMapping.put("product_deleted", ProductDeletedMessage.class);
        idClassMapping.put("product_updated", ProductUpdatedMessage.class);
        idClassMapping.put("brand_created", BrandCreatedMessage.class);
        idClassMapping.put("brand_deleted", BrandDeletedMessage.class);
        idClassMapping.put("category_created", CategoryCreatedMessage.class);
        idClassMapping.put("category_deleted", CategoryDeletedMessage.class);
        idClassMapping.put("promotion_price_start", PromotionPriceMessage.class);
        idClassMapping.put("promotion_price_reset", PromotionPriceResetMessage.class);

        idClassMapping.put("promotion_created", PromotionCreatedMessage.class);
        idClassMapping.put("promotion_deleted", PromotionDeletedMessage.class);
        idClassMapping.put("promotion_updated", PromotionUpdatedMessage.class);

        idClassMapping.put("inventory_updated", InventoryUpdatedMessage.class);
        idClassMapping.put("warehouse_created", WarehouseCreatedMessage.class);
        idClassMapping.put("warehouse_deleted", WarehouseDeletedMessage.class);

        idClassMapping.put("review_created", ReviewCreatedMessage.class);
        idClassMapping.put("review_deleted", ReviewDeletedMessage.class);

        classMapper.setIdClassMapping(idClassMapping);
        //classMapper.setIdClassMapping(Map.of("product_created", ProductCreatedDTO.class));
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }



}

