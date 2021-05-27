package com.thesis.gamamicroservices.productservice.dto.messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDeletedMessage {
    private int id;
}
