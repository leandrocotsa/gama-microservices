package com.thesis.gamamicroservices.userservice.dto;

import com.thesis.gamamicroservices.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreatedDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String sex;

    public UserCreatedDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.phoneNumber = user.getPhoneNumber();
        this.sex = user.getSex();
    }
}
