package com.thesis.gamamicroservices.ordersview.dto.messages.user_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdatedMessage {
    int userId;
    private Map<String, Object> updates;
}
