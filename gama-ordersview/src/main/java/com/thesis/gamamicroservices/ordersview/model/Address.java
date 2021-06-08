package com.thesis.gamamicroservices.ordersview.model;

import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.AddressCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//embedded no user
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    private int addressId;
    private String street;
    private String zipCode;
    private String country;
    private String city;

    public Address(AddressCreatedMessage addressCreatedMessage) {
        this.addressId = addressCreatedMessage.getAddressId();
        this.street = addressCreatedMessage.getStreet();
        this.zipCode = addressCreatedMessage.getZipCode();
        this.country = addressCreatedMessage.getCountry();
        this.city = addressCreatedMessage.getCity();
    }
}
