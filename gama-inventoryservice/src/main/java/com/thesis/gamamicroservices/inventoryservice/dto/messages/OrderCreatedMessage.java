package com.thesis.gamamicroservices.inventoryservice.dto.messages;

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
public class OrderCreatedMessage {
    private int orderId;
    private Date buyDate;
    private Map<Integer,Integer> products_qty;
    private Map<Integer,String> products_name;
    private Map<Integer,Double> products_price;
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
