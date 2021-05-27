package com.thesis.gamamicroservices.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentOrderSetDTO {
    private String currency;
    private String method;
    private String intent;
    private String description;
    private int orderID;
    private String idempotencyKey;

}