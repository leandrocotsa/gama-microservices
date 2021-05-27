package com.thesis.gamamicroservices.shoppingcartservice.messaging;

public enum RoutingKeys {

    ADDED("item.added"), REMOVED("item.removed");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
