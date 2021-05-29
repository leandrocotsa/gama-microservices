package com.thesis.gamamicroservices.orderservice.service;

import com.thesis.gamamicroservices.orderservice.dto.messages.PromotionPriceMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.PromotionPriceResetMessage;
import com.thesis.gamamicroservices.orderservice.dto.messages.StockCheckMessage;
import com.thesis.gamamicroservices.orderservice.model.foreign.ProductReplica;
import com.thesis.gamamicroservices.orderservice.repository.ProductReplicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventsService {

    private final ProductReplicaRepository productRepository;
    private final OrderService orderService;

    @Autowired
    public EventsService(ProductReplicaRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    /**-----------CREATE DELETE OPS----------**/

    public void productCreated(ProductReplica product) {
        productRepository.save(product);
    }

    public void productDeleted(int productId) {
        productRepository.deleteById(productId);
    }

    public void stockChecked(StockCheckMessage stockCheckMessage){
        orderService.processStock(stockCheckMessage);
    }

    /**-----------UPDATE OPS----------**/

    public void promotionStarted(PromotionPriceMessage promotionStarted)  {
        for (Map.Entry<Integer, Double> entry : promotionStarted.getProductsIds_and_prices().entrySet()) {
            try {
                ProductReplica p = orderService.getProductById(entry.getKey());
                p.setPromotionPrice(entry.getValue());
                productRepository.save(p);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void promotionEnded(PromotionPriceResetMessage productsEnded) {
        for(int pId : productsEnded.getProductsEnded()) {
            try {
                ProductReplica product = orderService.getProductById(pId);
                product.setPromotionPrice(null);
                productRepository.save(product);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void priceUpdated(int productId, Double newPrice) {
        try {
            ProductReplica product = orderService.getProductById(productId);
            product.setPrice(newPrice);
            productRepository.save(product);
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
    }


    public void paymentConfirmed(int orderId) {
        try {
            orderService.paymentConfirmed(orderId);
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
    }

}
