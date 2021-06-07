package com.thesis.gamamicroservices.userservice.model;

import com.thesis.gamamicroservices.userservice.dto.AddressSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String zipCode;
    private String country;
    private String city;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(AddressSetDTO addressSetDTO) {
        this.street = addressSetDTO.getStreet();
        this.zipCode = addressSetDTO.getZipCode();
        this.country = addressSetDTO.getCountry();
        this.city = addressSetDTO.getCity();
    }

}
