package com.thesis.gamamicroservices.paymentservice.dto.messages;

import com.thesis.gamamicroservices.paymentservice.model.PaymentOrder;
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

    public PaymentCreatedMessage(PaymentOrder paymentOrder) {
        this.paymentId = paymentOrder.getId();
        this.price = paymentOrder.getPrice();
        this.currency = paymentOrder.getCurrency();
        this.method = paymentOrder.getMethod();
        this.intent = paymentOrder.getIntent();
        this.description = paymentOrder.getDescription();
        this.payDate = paymentOrder.getPayDate();
        this.state = paymentOrder.getState().name();
        this.orderId = paymentOrder.getOrderId();
    }
}
