package com.thesis.gamamicroservices.orderservice.dto.messages.consumed;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDeletedMessage {
    private int id;
}
