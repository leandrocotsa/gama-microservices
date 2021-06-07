package com.thesis.gamamicroservices.userservice.dto.messages;

import com.thesis.gamamicroservices.userservice.model.Address;
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

    public AddressCreatedMessage(Address address) {
        this.addressId = address.getId();
        this.userId = address.getUser().getId();
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry();
        this.city = address.getCity();
    }
}
