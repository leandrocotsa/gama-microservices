package com.thesis.gamamicroservices.productsview.dto.messages.promotion_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionCreatedMessage {
    private int promotionId;
    private String name;
    private String description;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
    private List<Integer> productsIDs;

}
