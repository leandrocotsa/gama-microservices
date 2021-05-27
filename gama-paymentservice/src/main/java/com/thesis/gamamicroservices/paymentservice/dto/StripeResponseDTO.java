package com.thesis.gamamicroservices.paymentservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StripeResponseDTO {
    private String clientSecret;
}
