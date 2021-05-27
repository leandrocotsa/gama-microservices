package com.thesis.gamamicroservices.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingReferenceSetDTO {
    private Double costPerkg;
    private String country;
}
