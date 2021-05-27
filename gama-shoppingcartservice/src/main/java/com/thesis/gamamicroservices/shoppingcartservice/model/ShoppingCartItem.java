package com.thesis.gamamicroservices.shoppingcartservice.model;

import com.thesis.gamamicroservices.shoppingcartservice.dto.ShoppingCartItemSetDTO;
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
@Table(name="shoppingcart_item")
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int qty;
    @ManyToOne
    @JoinColumn(name = "shoppingcart_id")
    private ShoppingCart shoppingcart;

    private int productId;



    public ShoppingCartItem(ShoppingCartItemSetDTO orderItemSetDTO) {
        this.qty = orderItemSetDTO.getQty();
        this.productId = orderItemSetDTO.getProductId();

    }
}
