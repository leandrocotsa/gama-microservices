package com.thesis.gamamicroservices.paymentservice.model.foreign;

import com.thesis.gamamicroservices.paymentservice.dto.messages.OrderConfirmedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="order_replica")
public class ConfirmedOrderReplica {
    @Id
    private int orderId;
    private Double price;

    public ConfirmedOrderReplica(OrderConfirmedMessage orderConfirmedMessage) {
        this.orderId = orderConfirmedMessage.getOrderId();
        this.price = orderConfirmedMessage.getPrice();
    }
}
