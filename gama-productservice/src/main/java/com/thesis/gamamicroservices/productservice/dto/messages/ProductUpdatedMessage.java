package com.thesis.gamamicroservices.productservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductUpdatedMessage {
    private Map<String, Object> updates;
}
