package com.thesis.gamamicroservices.paymentservice.model;

import com.thesis.gamamicroservices.paymentservice.dto.PaymentOrderSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="paymentOrder")
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private Date payDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus state;
    private int orderId;


    public PaymentOrder(PaymentOrderSetDTO paymentOrderSetDTO, Double totalPrice) {
        this.currency = paymentOrderSetDTO.getCurrency();
        this.price = totalPrice;
        this.method = paymentOrderSetDTO.getMethod();
        this.intent = paymentOrderSetDTO.getIntent();
        this.description = paymentOrderSetDTO.getDescription();
        this.state = PaymentStatus.PENDING;
        this.orderId = paymentOrderSetDTO.getOrderID();
    }

}
