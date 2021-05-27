package com.thesis.gamamicroservices.promotionservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionSetDTO {
    private String name;
    private String description;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
    private List<Integer> productsIDs;
}
