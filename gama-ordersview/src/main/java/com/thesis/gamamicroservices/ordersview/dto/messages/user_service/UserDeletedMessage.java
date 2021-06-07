package com.thesis.gamamicroservices.ordersview.dto.messages.user_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDeletedMessage {
    int userId;
}
