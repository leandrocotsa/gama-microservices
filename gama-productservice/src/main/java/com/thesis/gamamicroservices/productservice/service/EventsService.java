package com.thesis.gamamicroservices.productservice.service;

import com.thesis.gamamicroservices.productservice.dto.PromotionStartedMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    private final ProductService productService;

    @Autowired
    public EventsService(ProductService productService) {
        this.productService = productService;
    }

    public void promotionStarted(PromotionStartedMessageDTO promotionStarted) {
            productService.setPromotionPrice(promotionStarted);
    }

    public void promotionEnded(List<Integer> productsEnded) {
            productService.resetPromotionPrice(productsEnded);
    }
/**
    public void productRemovedFromActivePromotion(int productId) {
        productService.resetPromotionPrice(productId);
    }
 **/
}
