package com.thesis.gamamicroservices.orderservice.model;

import com.thesis.gamamicroservices.orderservice.dto.OrderItemSetDTO;
import com.thesis.gamamicroservices.orderservice.model.foreign.ProductReplica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int qty;
    private double priceAtBuyTime; //sou eu que incializo com product.getPrice
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    //podia ter referencia à replica mas se o produto fosse apagado perdia toda a informação de que artigo foi comprado
    /**
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    **/

    private String productName;
    private int productId;
    private float weight;

    public OrderItem(OrderItemSetDTO orderItemSetDTO, ProductReplica p) {
        this.qty = orderItemSetDTO.getQty();
        this.productName = p.getName();
        this.productId = p.getId();
        this.weight = p.getWeight();
        if(p.getPromotionPrice() == null) {
            this.priceAtBuyTime = p.getPrice();
        } else {
            this.priceAtBuyTime = p.getPromotionPrice();
        }

    }
/**
    public OrderItem(ShoppingCartItem shoppingCartItem) {
        this.qty = shoppingCartItem.getQty();
        //this.product = shoppingCartItem.getProduct();
        this.productName = shoppingCartItem.getProduct().getName();
        this.productId = shoppingCartItem.getProduct().getId();
        this.weight = shoppingCartItem.getProduct().getWeight();
        if(shoppingCartItem.getProduct().getPromotionPrice() == null) {
            this.priceAtBuyTime = shoppingCartItem.getProduct().getPrice();
        } else {
            this.priceAtBuyTime = shoppingCartItem.getProduct().getPromotionPrice();
        }

    }
 **/
}
