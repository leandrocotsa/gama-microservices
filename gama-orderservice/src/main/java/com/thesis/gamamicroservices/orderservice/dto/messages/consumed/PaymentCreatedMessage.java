package com.thesis.gamamicroservices.orderservice.dto.messages.consumed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCreatedMessage {
    private int paymentId;
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private Date payDate;
    private String state;
    private int orderId;
}
