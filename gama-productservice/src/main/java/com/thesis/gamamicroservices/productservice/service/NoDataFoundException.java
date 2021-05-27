package com.thesis.gamamicroservices.productservice.service;

public class NoDataFoundException extends Exception {

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException() {
        super();
    }

}