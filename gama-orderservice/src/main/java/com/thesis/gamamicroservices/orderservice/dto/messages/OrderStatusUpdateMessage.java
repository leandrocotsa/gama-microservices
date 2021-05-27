package com.thesis.gamamicroservices.orderservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//para a view only
public class OrderStatusUpdateMessage {
    int orderId;
    String orderStatus; //rejected, expired, shipped
}
