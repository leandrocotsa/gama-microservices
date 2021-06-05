package com.thesis.gamamicroservices.productsview.messaging;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.BrandCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.BrandDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.CategoryCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.CategoryDeletedMessage;
import com.thesis.gamamicroservices.productsview.messaging.service.BrandsCategoriesEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues="brandsCategoriesProductsViewQueue")
public class BrandCategoryOpsReceiver {

    private Logger logger = LoggerFactory.getLogger(BrandCategoryOpsReceiver.class);

    private static final String BRAND_CREATED_LOG = "Brand created event. Brand: {}";
    private static final String BRAND_DELETED_LOG = "Brand deleted event. Brand: {}";
    private static final String CATEGORY_CREATED_LOG = "Category created event. Category: {}";
    private static final String CATEGORY_DELETED_LOG = "Category deleted event. Category: {}";

    @Autowired
    BrandsCategoriesEventsService brandsCategoriesEventsService;

    @RabbitHandler
    public void brandCreated(BrandCreatedMessage brandCreatedMessage) {
        logger.info(BRAND_CREATED_LOG, brandCreatedMessage.getId());
        brandsCategoriesEventsService.createBrand(brandCreatedMessage);
    }

    @RabbitHandler
    public void brandDeleted(BrandDeletedMessage brandDeletedMessage) {
        logger.info(BRAND_DELETED_LOG, brandDeletedMessage.getId());
        brandsCategoriesEventsService.deleteBrand(brandDeletedMessage);
    }

    @RabbitHandler
    public void categoryCreated(CategoryCreatedMessage categoryCreatedMessage) {
        logger.info(CATEGORY_CREATED_LOG, categoryCreatedMessage.getId());
        brandsCategoriesEventsService.createCategory(categoryCreatedMessage);
    }

    @RabbitHandler
    public void brandDeleted(CategoryDeletedMessage categoryDeletedMessage) {
        logger.info(CATEGORY_DELETED_LOG, categoryDeletedMessage.getId());
        brandsCategoriesEventsService.deleteCategory(categoryDeletedMessage);
    }
}
