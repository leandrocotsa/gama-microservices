package com.thesis.gamamicroservices.userservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDeletedMessage {
    int addressId;
    int userId;
}
