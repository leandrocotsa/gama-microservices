package com.thesis.gamamicroservices.promotionservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductReferenceSetDTO {
    private List<Integer> productsIDs;
}
