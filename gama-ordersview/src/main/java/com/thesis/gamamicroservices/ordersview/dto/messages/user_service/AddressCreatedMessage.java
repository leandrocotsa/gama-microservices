package com.thesis.gamamicroservices.ordersview.dto.messages.user_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressCreatedMessage {
    private int addressId;
    private int userId;
    private String street;
    private String zipCode;
    private String country;
    private String city;
}
