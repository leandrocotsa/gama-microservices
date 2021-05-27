package com.thesis.gamamicroservices.inventoryservice.service;

public class AlreadyExistsException extends Exception {

    AlreadyExistsException(String message) {
        super(message);
    }
}