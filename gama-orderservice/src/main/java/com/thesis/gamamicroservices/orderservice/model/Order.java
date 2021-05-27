package com.thesis.gamamicroservices.orderservice.model;

import com.thesis.gamamicroservices.orderservice.dto.OrderSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date buyDate;

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int userId;
    private String email;

    //tem que ter address, os address do user so servem para dar para escolher um desses mas tem que haver um associado à order pq o user pode ter varios
    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Shipping shipping;

    private int paymentOrderId;


    Double totalPrice;
    float totalWeight;

    public Order(OrderSetDTO orderSetDTO, int userId, String email) {
        this.buyDate = new Date(Calendar.getInstance().getTimeInMillis());
        this.orderStatus = OrderStatus.PENDING_STOCK;
        this.userId = userId;
        this.email = email;
        this.orderItems = new ArrayList<>();
    }

    /**
    public Order(ShoppingCart shoppingCart, User user) {
        this.buyDate = new Date(Calendar.getInstance().getTimeInMillis());
        this.orderStatus = OrderStatus.PENDING;
        this.user = user;
    }

**/

    public void setAllOrderItems(List<OrderItem> orderItems) {
        Double sumPrice = 0.0;
        float sumWeight = 0;
        for (OrderItem orderItem: orderItems) {
            orderItem.setOrder(this);
            sumPrice += orderItem.getPriceAtBuyTime() * orderItem.getQty();
            sumWeight += orderItem.getWeight() * orderItem.getQty();
        }
        this.orderItems = orderItems;
        this.totalPrice = sumPrice;
        this.totalWeight = sumWeight;
    }

    public void addShippingToOrder (Shipping shipping){
        shipping.setOrder(this);
        this.setShipping(shipping);
    }

    public void addPaymentToOrder (int id){
        //paymentOrder.setOrder(this);
        this.paymentOrderId = id;
    }

    public Double calculateTotalValueToPay() {
        return this.totalPrice + this.shipping.getCost();
    }

}
