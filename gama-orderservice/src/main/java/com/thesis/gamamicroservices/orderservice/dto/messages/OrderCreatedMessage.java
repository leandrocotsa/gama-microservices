package com.thesis.gamamicroservices.orderservice.dto.messages;

import com.thesis.gamamicroservices.orderservice.model.Order;
import com.thesis.gamamicroservices.orderservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashMap;
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

    public OrderCreatedMessage(Order order) {
        this.orderId = order.getId();
        this.buyDate = order.getBuyDate();
        products_qty = new HashMap<>();
        for(OrderItem o : order.getOrderItems()) {
            products_qty.put(o.getProductId(), o.getQty());
        }
        this.orderStatus = order.getOrderStatus().name();
        this.userId = order.getUserId();
        this.email = order.getEmail();
        this.totalPrice = order.getTotalPrice();
        this.totalWeight = order.getTotalWeight();
        this.paymentOrderId = order.getPaymentOrderId();
        this.shippingId = order.getShipping().getId();
        this.shippingCost = order.getShipping().getCost();
        this.shippingNotes = order.getShipping().getNotes();
        this.shippingCountry = order.getShipping().getCountry();
        this.shippingAddress = order.getShipping().getAddress();
    }
}
