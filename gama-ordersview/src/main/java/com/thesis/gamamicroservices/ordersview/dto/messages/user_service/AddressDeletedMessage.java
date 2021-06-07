package com.thesis.gamamicroservices.ordersview.dto.messages.user_service;

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
