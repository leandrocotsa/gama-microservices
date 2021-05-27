package com.thesis.gamamicroservices.userservice.model;

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

    public Address(String street, String zipCode, String country, String city) {
        this.street = street;
        this.zipCode = zipCode;
        this.country = country;
        this.city = city;
    }
}
