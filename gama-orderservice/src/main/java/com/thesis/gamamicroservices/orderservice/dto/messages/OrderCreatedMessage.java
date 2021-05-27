package com.thesis.gamamicroservices.orderservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//para a view e para o inventory
//fanout
public class OrderCreatedMessage {
    private int orderId;
    private Date buyDate;
    private Map<Integer,Integer> products_qty;
    private String orderStatus;
    private int userId;
    private String email;
    Double totalPrice;
    float totalWeight;
    private int paymentOrderId;

    private int shippingId;
    private Double shippingCost;
    private String shippingNotes;
    private String shippingCountry;
    private String shippingAddress;
}
