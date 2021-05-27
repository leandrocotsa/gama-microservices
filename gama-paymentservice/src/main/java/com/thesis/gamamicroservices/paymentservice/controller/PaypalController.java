package com.thesis.gamamicroservices.paymentservice.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.thesis.gamamicroservices.paymentservice.dto.PaymentOrderSetDTO;
import com.thesis.gamamicroservices.paymentservice.service.AlreadyPayedException;
import com.thesis.gamamicroservices.paymentservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.paymentservice.service.PaypalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(tags = "PaypalController")
public class PaypalController {

    private final PaypalService service;

    private static final String SUCCESS_URL = "/pay/paypal/success";
    private static final String CANCEL_URL = "/pay/paypal/cancel";

    @Autowired
    public PaypalController(PaypalService service) {
        this.service = service;
    }

    @PostMapping(value = "/pay/paypal")
    public String payment(@RequestBody PaymentOrderSetDTO paymentOrder) throws AlreadyPayedException {
        try {
            Payment payment = service.preparePayment(paymentOrder, SUCCESS_URL, CANCEL_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException | NoDataFoundException e) {

            e.printStackTrace();
        }
        return "didnt work out"; //isto é para onde vai depois do site do paypal, ou seja quero por uma página do frontend, maybe orders
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        System.out.println("canceled");
        return "cancel"; //isto é para onde vai depois do site do paypal, ou seja quero por uma página do frontend, maybe orders
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("orderID") int orderID, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            System.out.println(orderID);
            service.updateOrder(orderID); //COMO É QUE SEI O QUE FOI PAGO PARA MUDAR O STATUS
            //chamar order service que poe order como approved
            //no paymentOrder, atualiza o payment time e guarda-o
            //em microserviços eu publicaria aqui um evento de order payed no oayment service e o order services subscreveria
            if (payment.getState().equals("approved")) {
                return "redirect:https://www.google.com/"; //isto é para onde vai depois do site do paypal, ou seja quero por uma página do frontend, maybe orders
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "didnt work out"; //isto é para onde vai depois do site do paypal, ou seja quero por uma página do frontend, maybe orders
    }

}