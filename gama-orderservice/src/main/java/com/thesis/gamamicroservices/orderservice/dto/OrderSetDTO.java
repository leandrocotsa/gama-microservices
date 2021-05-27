package com.thesis.gamamicroservices.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSetDTO {

    private List<OrderItemSetDTO> orderItems;
    private String country;
    private String address;

}
