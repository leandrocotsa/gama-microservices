package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.promotion_service.PromotionUpdatedMessage;
import com.thesis.gamamicroservices.productsview.model.Promotion;
import com.thesis.gamamicroservices.productsview.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionEventsService {

    @Autowired
    PromotionRepository promotionRepository;

    public void createPromotion(PromotionCreatedMessage promotionCreated) {
        promotionRepository.save(new Promotion(promotionCreated));

    }

    public void deletePromotion(PromotionDeletedMessage promotionDeleted) {
        promotionRepository.deleteById(promotionDeleted.getPromotionId());
    }

    public void updatePromotion(PromotionUpdatedMessage promotionUpdated) {
        Promotion promotion = promotionRepository.findById(promotionUpdated.getPromotionId()).get();
        promotion.setProducts(promotionUpdated.getAllProducts());
        promotionRepository.save(promotion);
    }

}
