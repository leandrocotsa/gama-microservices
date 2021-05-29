package com.thesis.gamamicroservices.paymentservice.service;

import com.thesis.gamamicroservices.paymentservice.dto.messages.OrderConfirmedMessage;
import com.thesis.gamamicroservices.paymentservice.model.foreign.ConfirmedOrderReplica;
import com.thesis.gamamicroservices.paymentservice.repository.ConfirmedOrderReplicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    private final ConfirmedOrderReplicaRepository confirmedOrderReplicaRepository;

    public EventsService(ConfirmedOrderReplicaRepository confirmedOrderReplicaRepository) {
        this.confirmedOrderReplicaRepository = confirmedOrderReplicaRepository;
    }


    public void saveOrderConfirmed(OrderConfirmedMessage orderConfirmedMessage) {
        ConfirmedOrderReplica orderReplica = new ConfirmedOrderReplica(orderConfirmedMessage);
        confirmedOrderReplicaRepository.save(orderReplica);
    }

    public void orderExpired(int orderId) {
        confirmedOrderReplicaRepository.deleteById(orderId);
    }

}
