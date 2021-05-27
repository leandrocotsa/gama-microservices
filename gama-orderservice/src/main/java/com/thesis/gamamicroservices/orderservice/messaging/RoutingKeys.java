package com.thesis.gamamicroservices.orderservice.messaging;

public enum RoutingKeys {

    CREATED("order.created"), VERIFY_STOCK("order.verifystock");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
