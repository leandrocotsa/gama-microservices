package com.thesis.gamamicroservices.productsview.exceptions;

public class NoDataFoundException extends Exception {

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException() {
        super();
    }

}