package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.*;
import com.thesis.gamamicroservices.productsview.exceptions.NoDataFoundException;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.repository.ProductRepository;
import com.thesis.gamamicroservices.productsview.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductsEventsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    public void productCreated(ProductCreatedMessage productCreatedMessage) {
        Product product = new Product(productCreatedMessage);
        productRepository.save(product);
    }

    public void productDeleted(ProductDeletedMessage productDeletedMessage) {
        productRepository.deleteById(productDeletedMessage.getId());
    }

    public void editProduct(ProductUpdatedMessage productUpdates) {
        Map<String, Object> updates = productUpdates.getUpdates();
        int productId = (Integer)updates.get("id");
        Product product = productRepository.findByProductId(productId).get();
        try {
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on manager and set it to value v
                try {
                    Field field = ReflectionUtils.findField(Product.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, product, v);
                } catch (NullPointerException e) {
                    if (!(k.equals("brandId") || k.equals("categoryId"))) {
                        throw new NullPointerException();
                    }
                }
            });
            productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void promotionStarted(PromotionPriceMessage promotionStarted)  {
        for (Map.Entry<Integer, Double> entry : promotionStarted.getProductsIds_and_prices().entrySet()) {
            try {
                Product p = productService.getProductById(entry.getKey());
                p.setPromotionPrice(entry.getValue());
                p.setPromotionId(promotionStarted.getPromotionId());
                productRepository.save(p);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void promotionEnded(PromotionPriceResetMessage productsEnded) {
        for(int pId : productsEnded.getProductsEnded()) {
            try {
                Product product = productService.getProductById(pId);
                product.setPromotionPrice(null);
                product.setPromotionId(null);
                productRepository.save(product);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
