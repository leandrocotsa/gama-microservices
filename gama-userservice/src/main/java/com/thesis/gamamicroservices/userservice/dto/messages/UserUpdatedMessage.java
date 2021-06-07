package com.thesis.gamamicroservices.userservice.dto.messages;

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
