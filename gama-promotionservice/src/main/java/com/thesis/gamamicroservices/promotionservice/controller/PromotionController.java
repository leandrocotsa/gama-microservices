package com.thesis.gamamicroservices.promotionservice.controller;


import com.thesis.gamamicroservices.promotionservice.dto.ProductReferenceSetDTO;
import com.thesis.gamamicroservices.promotionservice.dto.PromotionSetDTO;
import com.thesis.gamamicroservices.promotionservice.service.EditActivePromotionException;
import com.thesis.gamamicroservices.promotionservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.promotionservice.service.PromotionConflictException;
import com.thesis.gamamicroservices.promotionservice.service.PromotionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/promotions")
@Api(tags = "PromotionController")
public class PromotionController {

    private static final Logger logger = LoggerFactory.getLogger(PromotionController.class);

    private static final String PROMOTION_CREATED_LOG = "A Promotion was created";
    private static final String PROMOTION_DELETED_LOG = "Promotion: {} was deleted";
    private static final String PROMOTION_UPDATED_LOG = "Promotion: {} was updated";
    private static final String PROMOTION_STARTED_LOG = "Promotion: {} was manually started";
    private static final String PROMOTION_ENDED_LOG = "Promotion: {} was manually ended";
    private static final String PRODUCTS_ADDED_LOG = "Products: {} added to promotion";
    private static final String PRODUCTS_REMOVED_LOG = "Products {} removed from promotion";

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Operation(summary = "Deletes an existing promotion")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePromotion(@PathVariable int id) throws NoDataFoundException {
        promotionService.deletePromotion(id);
        logger.info(PROMOTION_DELETED_LOG, id);
    }

    @Operation(summary = "Creates a new promotion")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createPromotion(@RequestBody PromotionSetDTO promotionSetDTO) throws PromotionConflictException {
        promotionService.createPromotion(promotionSetDTO);
        logger.info(PROMOTION_CREATED_LOG);
    }

    @Operation(summary = "Adds a new collection of products to an existing promotion")
    @PostMapping(path = "/{promotion_id}/products")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void addProductsToPromotion(@PathVariable int promotion_id, @RequestBody ProductReferenceSetDTO productReferenceSetDTO) throws NoDataFoundException, PromotionConflictException {
        promotionService.addProductsToPromotion(promotion_id, productReferenceSetDTO);
        logger.info(PRODUCTS_ADDED_LOG, productReferenceSetDTO.getProductsIDs());
    }

    @Operation(summary = "Removes a collection of products from an existing promotion")
    @DeleteMapping(path = "/{promotion_id}/products")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void removeProductFromPromotion(@PathVariable int promotion_id,  @RequestBody ProductReferenceSetDTO productReferenceSetDTO) throws NoDataFoundException {
        promotionService.removeProductFromPromotion(promotion_id, productReferenceSetDTO);
        logger.info(PRODUCTS_REMOVED_LOG, productReferenceSetDTO.getProductsIDs());
    }

    @Operation(summary = "Manual forces the start of a promotion")
    @PostMapping(path = "/{promotion_id}/start")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void startPromotion(@PathVariable int promotion_id) throws NoDataFoundException {
        promotionService.startPromotion(promotion_id);
        logger.info(PROMOTION_STARTED_LOG, promotion_id);
    }

    @Operation(summary = "Manual forces the end of a promotion")
    @PostMapping(path = "/{promotion_id}/end")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void endPromotion(@PathVariable int promotion_id) throws NoDataFoundException {
        promotionService.endPromotion(promotion_id);
        logger.info(PROMOTION_ENDED_LOG, promotion_id);
    }

    @Operation(summary = "Edits a promotion's details")
    @PatchMapping(path = "/{promotion_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void editPromotion(@RequestBody Map<String, Object> updates, @PathVariable int promotion_id) throws NoDataFoundException, EditActivePromotionException {
        promotionService.updatePromotion(updates, promotion_id);
        logger.info(PROMOTION_UPDATED_LOG, promotion_id);
    }


}
