package com.thesis.gamamicroservices.ordersview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shipping {
    private int shippingId;
    private Double shippingCost;
    private String shippingNotes;
    private String shippingCountry;
    private String shippingAddress;
}
