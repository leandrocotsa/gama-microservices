package com.thesis.gamamicroservices.inventoryservice.messaging;

public enum RoutingKeys {

    STOCK_CHECK("stock"),
    INVENTORY_UPDATED("inventory.updated"),
    WAREHOUSE_CREATED("warehouse.created"),
    WAREHOUSE_DELETED("warehouse.deleted");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
