package com.thesis.gamamicroservices.paymentservice.service;

import com.thesis.gamamicroservices.paymentservice.dto.PaymentOrderSetDTO;
import com.thesis.gamamicroservices.paymentservice.dto.messages.PaymentCreatedMessage;
import com.thesis.gamamicroservices.paymentservice.model.PaymentOrder;
import com.thesis.gamamicroservices.paymentservice.model.PaymentStatus;
import com.thesis.gamamicroservices.paymentservice.model.foreign.ConfirmedOrderReplica;
import com.thesis.gamamicroservices.paymentservice.repository.PaymentRepository;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

   @Autowired
    public PaymentService(PaymentRepository paymentRepository, RabbitTemplate rabbitTemplate, @Qualifier("paymentConfirmedExchange") Exchange exchange) {
        this.paymentRepository = paymentRepository;
       this.rabbitTemplate = rabbitTemplate;
       this.exchange = exchange;
   }

    public void addPaymentToOrder(PaymentOrderSetDTO paymentOrderSetDTO, ConfirmedOrderReplica order) {
        PaymentOrder paymentOrder = new PaymentOrder(paymentOrderSetDTO, order.getPrice());
        paymentRepository.save(paymentOrder);
    }

    public void paymentSuccessful(int orderID) {
        Optional<PaymentOrder> paymentOrder = paymentRepository.findByOrderId(orderID);
        paymentOrder.get().setPayDate(new Date(Calendar.getInstance().getTimeInMillis()));
        paymentOrder.get().setState(PaymentStatus.PAYED);
        paymentRepository.save(paymentOrder.get());
        rabbitTemplate.convertAndSend(exchange.getName(), "payment.confirmed", new PaymentCreatedMessage(paymentOrder.get()));//envia evento para a order por payed

    }

    public Optional<PaymentOrder> findPaymentByOrderId(int orderId) {
       return paymentRepository.findByOrderId(orderId);
    }

    //caso o pagamento tenha falhado este vai-se manter em pending
    //posso ter um scheduled job no orders service a ver se passaram x horas e o estado passa a expired
}
