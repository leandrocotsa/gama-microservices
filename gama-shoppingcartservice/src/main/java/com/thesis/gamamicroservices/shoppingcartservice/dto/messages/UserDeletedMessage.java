package com.thesis.gamamicroservices.shoppingcartservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDeletedMessage {
    int userId;
}
