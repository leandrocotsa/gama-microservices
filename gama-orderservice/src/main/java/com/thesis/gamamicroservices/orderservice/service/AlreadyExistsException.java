package com.thesis.gamamicroservices.orderservice.service;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String message) {
        super(message);
    }
}