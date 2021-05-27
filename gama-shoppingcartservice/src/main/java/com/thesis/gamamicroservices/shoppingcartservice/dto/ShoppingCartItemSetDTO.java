package com.thesis.gamamicroservices.shoppingcartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartItemSetDTO {
    private int qty;
    private int productId;

}