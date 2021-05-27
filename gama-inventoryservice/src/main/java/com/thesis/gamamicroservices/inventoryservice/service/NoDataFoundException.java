package com.thesis.gamamicroservices.inventoryservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NoDataFoundException extends Exception {

    NoDataFoundException(String message) {
        super(message);
    }
}