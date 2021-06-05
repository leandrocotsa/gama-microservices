package com.thesis.gamamicroservices.inventoryservice.messaging;

public enum RoutingKeys {

    STOCK_CHECK("stock"),
    INVENTORY_UPDATED("inventory"),
    WAREHOUSE_CREATED("warehouse"),
    WAREHOUSE_DELETED("warehouse");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
