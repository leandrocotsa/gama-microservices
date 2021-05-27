package com.thesis.gamamicroservices.shoppingcartservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoDataFoundException extends Exception {

    public NoDataFoundException(String message) {
        super(message);
    }
}