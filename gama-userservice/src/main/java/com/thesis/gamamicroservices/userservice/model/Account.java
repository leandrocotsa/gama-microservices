
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
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@Column(unique = true)
    //@Email
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role; //could be a list

    public Account(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
