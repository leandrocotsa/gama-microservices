package com.thesis.gamamicroservices.userservice.dto.messages;

import com.thesis.gamamicroservices.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreatedMessage {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String sex;
    private int accountId;
    private String email;
    private String role;

    public UserCreatedMessage(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.phoneNumber = user.getPhoneNumber();
        this.sex = user.getSex();
        this.accountId = user.getAccount().getId();
        this.email = user.getAccount().getEmail();
        this.role = user.getAccount().getRole().name();
    }
}
