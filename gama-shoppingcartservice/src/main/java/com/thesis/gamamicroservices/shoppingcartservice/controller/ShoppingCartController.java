package com.thesis.gamamicroservices.shoppingcartservice.controller;


import com.thesis.gamamicroservices.shoppingcartservice.dto.ShoppingCartItemSetDTO;
import com.thesis.gamamicroservices.shoppingcartservice.service.ShoppingCartService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Api(tags = "ShoppingCartController")
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    private static final String PRODUCT_ADDED_LOG = "Product: {} added to Shopping Cart";
    private static final String PRODUCT_REMOVED_LOG = "Product: {} removed from Shopping Cart";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addItemToShoppingCart(@RequestHeader("Authorization") String authorizationToken, @RequestBody ShoppingCartItemSetDTO itemSetDTO) {
        this.shoppingCartService.addItemToCart(authorizationToken, itemSetDTO);
        logger.info(PRODUCT_ADDED_LOG, itemSetDTO.getProductId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeItemFromCart(@RequestHeader("Authorization") String authorizationToken, @RequestParam int product_id) {
        this.shoppingCartService.removeItemFromCart(authorizationToken, product_id);
        logger.info(PRODUCT_REMOVED_LOG, product_id);
    }

    @DeleteMapping(path="/clean")
    @ResponseStatus(HttpStatus.OK)
    public void cleanCart(@RequestParam int userId) {
        this.shoppingCartService.cleanShoppingCart(userId);
    }

}
