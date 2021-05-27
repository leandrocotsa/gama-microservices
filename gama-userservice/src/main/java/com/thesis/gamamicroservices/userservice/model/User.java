package com.thesis.gamamicroservices.userservice.model;

import com.thesis.gamamicroservices.userservice.dto.UserSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String sex;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;

    public User(UserSetDTO userSetDTO, Account account) {
        this.firstName = userSetDTO.getFirstName();
        this.lastName = userSetDTO.getLastName();
        this.birthDate = userSetDTO.getBirthDate();
        this.phoneNumber = userSetDTO.getPhoneNumber();
        this.sex = userSetDTO.getSex();
        this.addresses = new ArrayList<>();
        this.account = account;
    }

    public void addAddressToUser(Address address) {
        address.setUser(this);
        this.addresses.add(address);
    }
}