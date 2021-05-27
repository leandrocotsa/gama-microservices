package com.thesis.gamamicroservices.paymentservice.controller;


import com.thesis.gamamicroservices.paymentservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.paymentservice.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Autowired
    StripeService stripeService;



    @PostMapping("/stripe/events")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws NoDataFoundException {
        //return stripeService.handleStripeEvents(payload, sigHeader);
        return new ResponseEntity<String>(stripeService.handleStripeEvents(payload, sigHeader), HttpStatus.OK);
    }

}
