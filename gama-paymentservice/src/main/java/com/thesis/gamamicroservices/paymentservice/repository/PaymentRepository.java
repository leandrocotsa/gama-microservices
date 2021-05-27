package com.thesis.gamamicroservices.paymentservice.repository;

import com.thesis.gamamicroservices.paymentservice.model.PaymentOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentOrder,Integer> {
    //PaymentOrder findByOrderId(int orderId);
    Optional<PaymentOrder> findByOrderId(int orderId);
}
