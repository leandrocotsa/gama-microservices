package com.thesis.gamamicroservices.userservice.dto;

import com.thesis.gamamicroservices.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String sex;
    //address ?

    public UserGetDTO(User user){
        this.email = user.getAccount().getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.phoneNumber = user.getPhoneNumber();
        this.sex = user.getSex();
    }
}
