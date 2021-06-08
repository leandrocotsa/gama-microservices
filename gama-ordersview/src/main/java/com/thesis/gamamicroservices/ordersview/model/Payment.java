package com.thesis.gamamicroservices.ordersview.model;


import com.thesis.gamamicroservices.ordersview.dto.messages.payment_service.PaymentCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//embedded na order
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    private int paymentId;
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private Date payDate;
    private String state;

    public Payment(PaymentCreatedMessage paymentCreatedMessage) {
        this.paymentId = paymentCreatedMessage.getPaymentId();
        this.price = paymentCreatedMessage.getPrice();
        this.currency = paymentCreatedMessage.getCurrency();
        this.method = paymentCreatedMessage.getMethod();
        this.intent = paymentCreatedMessage.getIntent();
        this.description = paymentCreatedMessage.getDescription();
        this.payDate = paymentCreatedMessage.getPayDate();
        this.state = paymentCreatedMessage.getState();
    }
}
