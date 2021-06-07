package com.thesis.gamamicroservices.ordersview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "orders")
public class Order {
    @Id
    private int orderId;
    private Date buyDate;
    private Map<Integer,Integer> products_qty;
    private String orderStatus;
    private int userId;
    private String email;
    Double totalPrice;
    float totalWeight;
    private int paymentOrderId;
}
