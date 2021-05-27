package com.thesis.gamamicroservices.orderservice.service;

public class NoDataFoundException extends Exception {

    public NoDataFoundException(String message) {
        super(message);
    }
}