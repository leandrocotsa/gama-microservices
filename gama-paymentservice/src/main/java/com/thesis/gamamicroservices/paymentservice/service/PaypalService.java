package com.thesis.gamamicroservices.paymentservice.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.thesis.gamamicroservices.paymentservice.dto.PaymentOrderSetDTO;
import com.thesis.gamamicroservices.paymentservice.model.PaymentOrder;
import com.thesis.gamamicroservices.paymentservice.model.PaymentStatus;
import com.thesis.gamamicroservices.paymentservice.model.foreign.ConfirmedOrderReplica;
import com.thesis.gamamicroservices.paymentservice.repository.ConfirmedOrderReplicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PaypalService {

    private final APIContext apiContext;
    private final ConfirmedOrderReplicaRepository confirmedOrderReplicaRepository;
    private final PaymentService paymentService;


    @Autowired
    public PaypalService(APIContext apiContext, ConfirmedOrderReplicaRepository confirmedOrderReplicaRepository, PaymentService paymentService) {
        this.apiContext = apiContext;
        this.confirmedOrderReplicaRepository = confirmedOrderReplicaRepository;
        this.paymentService = paymentService;
    }

    public Payment preparePayment(PaymentOrderSetDTO paymentOrderSetDTO, String successURL, String cancelURL) throws NoDataFoundException, PayPalRESTException, AlreadyPayedException {
        Optional<ConfirmedOrderReplica> order = confirmedOrderReplicaRepository.findById(paymentOrderSetDTO.getOrderID());
        Optional<PaymentOrder> payment = paymentService.findPaymentByOrderId(paymentOrderSetDTO.getOrderID());
        if(order.isPresent()) {
            if(payment.isEmpty() || payment.get().getState() != PaymentStatus.PAYED) {
                paymentService.addPaymentToOrder(paymentOrderSetDTO, order.get());
                return createPayment(order.get().getPrice(), paymentOrderSetDTO.getCurrency(), paymentOrderSetDTO.getMethod(),
                        paymentOrderSetDTO.getIntent(), paymentOrderSetDTO.getDescription(), "http://192.168.1.174:8080" + cancelURL + "?orderID=" + order.get().getOrderId(),
                        "http://192.168.1.174:8080" + successURL + "?orderID=" + order.get().getOrderId());
                //aqui tenho de por o ip da aplicação, o futuro virtual ip
                //como ainda nao o tenho vou por o ip de um nó
            } else {
                throw new AlreadyPayedException("Order: " + order.get().getOrderId() + " was already payed.");
            }
        } else {
            throw new NoDataFoundException("Order: " + paymentOrderSetDTO.getOrderID() + "does not exist");
        }

    }


    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format(Locale.US, "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }


    public void updateOrder(int orderID) {
        paymentService.paymentSuccessful(orderID);
    }

}