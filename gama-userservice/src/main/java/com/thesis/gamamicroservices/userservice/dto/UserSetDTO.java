package com.thesis.gamamicroservices.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSetDTO {
    @Email(message = "a valid email must be entered")
    @NotNull(message = "email must be entered")
    private String email;
    @NotNull
    private String password;
    @NotNull(message = "first name must be entered")
    private String firstName;
    @NotNull(message = "last name must be entered")
    private String lastName;
    private Date birthDate;
    private String phoneNumber; //should be optional
    private String sex; //should be optional...vem vazio i guess, e no builder faço build apenas do que quero idk
    //private String street;
    //private String zipCode;
    //private String country;
    //private String city;
}
