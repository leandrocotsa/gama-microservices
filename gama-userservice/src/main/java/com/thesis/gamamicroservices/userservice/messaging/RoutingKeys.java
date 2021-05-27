package com.thesis.gamamicroservices.userservice.messaging;

public enum RoutingKeys {

    CREATED("user.created"), DELETED("user.deleted");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
