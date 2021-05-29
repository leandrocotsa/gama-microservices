package com.thesis.gamamicroservices.orderservice.messaging;

public enum RoutingKeys {

    ORDER_CONFIRMED("order.confirmed"), ORDER_CREATED("order.created"), ORDER_UPDATED("order.updated");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
