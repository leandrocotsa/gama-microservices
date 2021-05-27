package com.thesis.gamamicroservices.productservice.messaging;

public enum RoutingKeys {

    PRODUCT_CREATED("product.created"),
    PRODUCT_DELETED("product.deleted"),
    PROMOTION_STARTED("product.promotion_started"),
    PROMOTION_ENDED("product.promotion_ended"),
    PRICE_UPDATED("product.price_updated"),
    PRODUCT_UPDATED("product.updated"),
    BRAND_CREATED("brand.created"),
    BRAND_DELETED("brand.deleted"),
    CATEGORY_CREATED("category.created"),
    CATEGORY_DELETED("category.deleted");

    String notation;

    RoutingKeys(String notation) {
        this.notation = notation;
    }

    public String getNotation() { return notation; }

}
