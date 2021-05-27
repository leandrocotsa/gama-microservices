package com.thesis.gamamicroservices.userservice.service;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String message) {
        super(message);
    }
}