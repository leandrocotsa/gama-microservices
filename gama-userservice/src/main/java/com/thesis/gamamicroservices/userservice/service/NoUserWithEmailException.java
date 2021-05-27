package com.thesis.gamamicroservices.userservice.service;

public class NoUserWithEmailException extends Exception {

    public NoUserWithEmailException(String message) {
        super(message);
    }
}