package com.thesis.gamamicroservices.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressSetDTO {
    private String street;
    private String zipCode;
    private String country;
    private String city;
}
