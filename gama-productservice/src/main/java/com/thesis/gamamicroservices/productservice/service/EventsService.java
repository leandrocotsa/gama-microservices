package com.thesis.gamamicroservices.productservice.service;

import com.thesis.gamamicroservices.productservice.dto.messages.consumed.PromotionEndedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.consumed.PromotionStartedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    private final ProductService productService;

    @Autowired
    public EventsService(ProductService productService) {
        this.productService = productService;
    }

    public void promotionStarted(PromotionStartedMessage promotionStarted) {
            productService.setPromotionPrice(promotionStarted);
    }

    public void promotionEnded(PromotionEndedMessage promotionEndedMessage) {
            productService.resetPromotionPrice(promotionEndedMessage.getProductsEnded());
    }
/**
    public void productRemovedFromActivePromotion(int productId) {
        productService.resetPromotionPrice(productId);
    }
 **/
}
