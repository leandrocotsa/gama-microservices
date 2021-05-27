package com.thesis.gamamicroservices.orderservice.dto.messages;

import com.thesis.gamamicroservices.orderservice.model.Order;
import com.thesis.gamamicroservices.orderservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//ordercreatedmessage antigo
public class OrderForStockCheckMessage {

    private int orderId;
    private Map<Integer,Integer> products_qty;

    public OrderForStockCheckMessage(Order order) {
        this.orderId = order.getId();
        products_qty = new HashMap<>();
        for(OrderItem o : order.getOrderItems()) {
            products_qty.put(o.getProductId(), o.getQty());
        }

    }
}
