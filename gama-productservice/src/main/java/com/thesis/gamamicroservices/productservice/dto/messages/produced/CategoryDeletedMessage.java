package com.thesis.gamamicroservices.productservice.dto.messages.produced;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDeletedMessage {
    private int id;
}
