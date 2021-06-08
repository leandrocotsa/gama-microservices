package com.thesis.gamamicroservices.ordersview.model;

import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.UserCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private int userId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String sex;
    private int accountId;
    private String email;
    private String role;
    private List<Address> addresses;

    public User(UserCreatedMessage userCreatedMessage) {
        this.userId = userCreatedMessage.getId();
        this.firstName = userCreatedMessage.getFirstName();
        this.lastName = userCreatedMessage.getLastName();
        this.birthDate = userCreatedMessage.getBirthDate();
        this.phoneNumber = userCreatedMessage.getPhoneNumber();
        this.sex = userCreatedMessage.getSex();
        this.accountId = userCreatedMessage.getAccountId();
        this.email = userCreatedMessage.getEmail();
        this.role = userCreatedMessage.getRole();
        this.addresses = new ArrayList<>();
    }
}
