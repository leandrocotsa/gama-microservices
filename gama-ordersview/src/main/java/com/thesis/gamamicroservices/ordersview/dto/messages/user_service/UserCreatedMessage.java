package com.thesis.gamamicroservices.ordersview.dto.messages.user_service;

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
}
