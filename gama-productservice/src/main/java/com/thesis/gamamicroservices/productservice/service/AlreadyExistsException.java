package com.thesis.gamamicroservices.productservice.service;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String message) {
        super(message);
    }
}