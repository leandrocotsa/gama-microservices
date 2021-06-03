package com.thesis.gamamicroservices.productsview.dto.messages.product_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductUpdatedMessage {
    private Map<String, Object> updates;
}
