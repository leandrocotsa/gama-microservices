package com.thesis.gamamicroservices.inventoryservice.service;

class NoStockException extends Exception {

    NoStockException(String message) {
        super(message);
    }
}