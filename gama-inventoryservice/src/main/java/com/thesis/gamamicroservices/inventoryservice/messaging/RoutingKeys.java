package com.thesis.gamamicroservices.inventoryservice.messaging;

public enum RoutingKeys {

    NOSTOCK("order.nostock"), STOCKAVAILABLE("order.stockavailable");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
