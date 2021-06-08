package com.thesis.gamamicroservices.ordersview.messaging.service;

import com.thesis.gamamicroservices.ordersview.dto.messages.payment_service.PaymentCreatedMessage;
import com.thesis.gamamicroservices.ordersview.model.Order;
import com.thesis.gamamicroservices.ordersview.model.Payment;
import com.thesis.gamamicroservices.ordersview.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventsService {

    @Autowired
    OrderRepository orderRepository;

    public void paymentConfirmed(PaymentCreatedMessage paymentCreatedMessage) {
        Order order = orderRepository.findById(paymentCreatedMessage.getOrderId()).get();
        order.setOrderStatus("APPROVED");
        order.setPayment(new Payment(paymentCreatedMessage));
        orderRepository.save(order);
    }

}
