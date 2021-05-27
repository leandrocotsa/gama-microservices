package com.thesis.gamamicroservices.paymentservice.service;

public class AlreadyPayedException extends Exception {

    public AlreadyPayedException(String message) {
        super(message);
    }
}
