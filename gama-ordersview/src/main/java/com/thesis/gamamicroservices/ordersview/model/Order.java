package com.thesis.gamamicroservices.ordersview.model;

import com.thesis.gamamicroservices.ordersview.dto.messages.order_service.OrderCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "orders")
public class Order {
    @Id
    private int orderId;
    private Date buyDate;
    private List<OrderItem> orderItems;
    private String orderStatus;
    private int userId;
    private String email;
    Double totalPrice;
    float totalWeight;
    private Payment payment;
    private Shipping shipping;


    public Order(OrderCreatedMessage orderCreatedMessage) {
        this.orderId = orderCreatedMessage.getOrderId();
        this.buyDate = orderCreatedMessage.getBuyDate();
        this.orderStatus = orderCreatedMessage.getOrderStatus();
        this.userId = orderCreatedMessage.getUserId();
        this.email = orderCreatedMessage.getEmail();
        this.totalPrice = orderCreatedMessage.getTotalPrice();
        this.totalWeight = orderCreatedMessage.getTotalWeight();
    }


}
