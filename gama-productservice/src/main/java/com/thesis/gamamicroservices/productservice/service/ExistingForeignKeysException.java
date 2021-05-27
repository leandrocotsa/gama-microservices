package com.thesis.gamamicroservices.productservice.service;

public class ExistingForeignKeysException extends Exception {
    public ExistingForeignKeysException(String message) {
        super(message);
    }
}
