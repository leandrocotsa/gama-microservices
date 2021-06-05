package com.thesis.gamamicroservices.promotionservice.service;


import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.promotionservice.dto.ProductReferenceSetDTO;
import com.thesis.gamamicroservices.promotionservice.dto.PromotionSetDTO;
import com.thesis.gamamicroservices.promotionservice.dto.messages.*;
import com.thesis.gamamicroservices.promotionservice.model.Promotion;
import com.thesis.gamamicroservices.promotionservice.model.PromotionState;
import com.thesis.gamamicroservices.promotionservice.repository.PromotionRepository;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ObjectWriter objectWriter;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange priceExchange;
    private final Exchange promotionExchange;

    @Autowired
    public PromotionService (PromotionRepository promotionRepository, ObjectWriter objectWriter, RabbitTemplate rabbitTemplate, @Qualifier("promotionPriceExchange") Exchange priceExchange, @Qualifier("promotionCUDExchange") Exchange promotionExchange){
        this.promotionRepository = promotionRepository;
        this.objectWriter = objectWriter;
        this.rabbitTemplate = rabbitTemplate;
        this.priceExchange = priceExchange;
        this.promotionExchange = promotionExchange;
    }


    public Promotion getPromotionById(int id) throws NoDataFoundException {
        Optional<Promotion> promotion = this.promotionRepository.findById(id);
        if (promotion.isPresent()) {
            return promotion.get();
        } else {
            throw new NoDataFoundException("There's no promotion with id " + id);
        }
    }


    public void createPromotion(PromotionSetDTO promotionSetDTO) throws PromotionConflictException {

        Promotion promotion = new Promotion(promotionSetDTO);
        List<Integer> products = new ArrayList<>();

        for (Integer pID : promotionSetDTO.getProductsIDs()) {
            if(!isProductAlreadyInAPromotion(pID)) {
                products.add(pID);
            } else {
                throw new PromotionConflictException("Product " + pID + " is already associated with a promotion");
            }
        }
        promotion.setProductsIds(products);
        promotionRepository.save(promotion);
        rabbitTemplate.convertAndSend(promotionExchange.getName(), "promotion", new PromotionCreatedMessage(promotion));

        //ugh isto vai ter de ser uma mensagem tambem para a view ter as promotions
    }

    //so permito apagar uma promoção se ela nao estiver ativa
    //se quiser apagar uma promoção ativa tenho que a terminar primeiro
    //assim evito ter tambem de propagar eliminação de pormoções que ate ja podem ter acabado ou nem começado
    public void deletePromotion(int promotionID) throws NoDataFoundException {
        Promotion p = this.getPromotionById(promotionID);
        promotionRepository.delete(p);
        if(p.getState().equals(PromotionState.ACTIVE)) {
            rabbitTemplate.convertAndSend(priceExchange.getName(), "promotion", new PromotionEndedMessage(p.getProductsIds()));
        }
        rabbitTemplate.convertAndSend(promotionExchange.getName(), "promotion", new PromotionDeletedMessage(promotionID));
    }


    //edit promotion (value, name and description)

    public void addProductsToPromotion(int promotionID, ProductReferenceSetDTO productReferenceSetDTO) throws NoDataFoundException, PromotionConflictException {
        Promotion promotion = this.getPromotionById(promotionID);
        List<Integer> newProducts = new ArrayList<>();

        for (Integer pID : productReferenceSetDTO.getProductsIDs()) {
            if(!isProductAlreadyInAPromotion(pID)) {
                newProducts.add(pID);
            } else {
                throw new PromotionConflictException("Product " + pID + " is already associated with a promotion");
            }
        }
        List<Integer> allProducts = Stream.concat(promotion.getProductsIds().stream(), newProducts.stream())
                .collect(Collectors.toList());
        promotion.setProductsIds(allProducts);
        promotionRepository.save(promotion);

        if(promotion.getState().equals(PromotionState.ACTIVE)) {
            rabbitTemplate.convertAndSend(priceExchange.getName(), "promotion", new PromotionStartedMessage(newProducts, promotion.getDiscountAmount()));

            /**
            try {
                String productsJson = objectWriter.writeValueAsString(new PromotionStartedMessage(newProducts, promotion.getDiscountAmount()));
                rabbitTemplate.convertAndSend(exchange.getName(), "promotion.started", productsJson);
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
             **/
        }
        rabbitTemplate.convertAndSend(promotionExchange.getName(), "promotion", new PromotionUpdatedMessage(allProducts));

    }

    private boolean isProductAlreadyInAPromotion(int productId) {
        Optional<Promotion> matchingObject = promotionRepository.findActiveOrScheduledPromotions().stream().
                filter(p -> p.getProductsIds().contains(productId)).
                findFirst();

        return matchingObject.isPresent();
    }

    public void removeProductFromPromotion(int promotionID, ProductReferenceSetDTO productReferenceSetDTO) throws NoDataFoundException {

        /**
        Promotion promotion = this.getPromotionById(promotionID);
        if(promotion.getProductsIds().contains(productID)) {
            promotion.getProductsIds().remove(Integer.valueOf(productID));
            promotionRepository.save(promotion);

            if(promotion.getState().equals(PromotionState.ACTIVE)) {
                rabbitTemplate.convertAndSend(exchange.getName(), "promotion.removeproduct", productID);
            }
        }
**/


        Promotion promotion = this.getPromotionById(promotionID);
        List<Integer> removedProducts = new ArrayList<>(productReferenceSetDTO.getProductsIDs());

        for (Integer pID : productReferenceSetDTO.getProductsIDs()) {
            if(promotion.getProductsIds().contains(pID)) {
                promotion.getProductsIds().remove(pID);
            } else {
                removedProducts.remove(pID);
            }
        }
        promotionRepository.save(promotion);

        if(promotion.getState().equals(PromotionState.ACTIVE)) {
            rabbitTemplate.convertAndSend(priceExchange.getName(), "promotion", new PromotionEndedMessage(removedProducts));
        }
        rabbitTemplate.convertAndSend(promotionExchange.getName(), "promotion", new PromotionUpdatedMessage(promotion.getProductsIds()));

        //SE A PROMOÇÃO JA ESTIVER EM ACTIVE ENTAO MANDO EVENTO, SENAO NAO
    }

    //triggered by a scheduled job?
    public void endPromotion(int promotionID) throws NoDataFoundException {
        Promotion p = this.getPromotionById(promotionID);
        p.setState(PromotionState.EXPIRED);
        this.promotionRepository.save(p);
        ArrayList<Integer> products = new ArrayList<>(p.getProductsIds());
        rabbitTemplate.convertAndSend(priceExchange.getName(), "promotion", new PromotionEndedMessage(products));

        //ENVIAR EVENTO COM LISTA DE IDS PARA O PRODUCT SERVICE MUDAR OS PREÇOS
    }

    //triggered by a scheduled job?
    public void startPromotion(int promotionID) throws NoDataFoundException {
        Promotion p = this.getPromotionById(promotionID);
        p.setState(PromotionState.ACTIVE);
        this.promotionRepository.save(p);
        rabbitTemplate.convertAndSend(priceExchange.getName(), "promotion", new PromotionStartedMessage(p));
        /**
        try {
            String productsJson = objectWriter.writeValueAsString(new PromotionStartedMessage(p));
            rabbitTemplate.convertAndSend(exchange.getName(), "promotion.started", productsJson);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
         **/
    }

    public void updatePromotion(Map<String, Object> updates, int promotion_id) throws NoDataFoundException, EditActivePromotionException {
        Promotion promotion = this.getPromotionById(promotion_id);
        if(!promotion.getState().equals(PromotionState.ACTIVE)) {
            try {
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on manager and set it to value v
                    Field field = ReflectionUtils.findField(Promotion.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, promotion, v);
                });
                promotionRepository.save(promotion);
            } catch (Exception e) {
                e.printStackTrace();
                throw new NoDataFoundException("Invalid arguments");
            }
        } else {
            throw new EditActivePromotionException("You cannot update an active promotion");
        }
    }

}
