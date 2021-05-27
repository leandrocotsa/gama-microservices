package com.thesis.gamamicroservices.shoppingcartservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemAddedDTO {

    private int userId;
    private int productId;
    private int qty;
}
